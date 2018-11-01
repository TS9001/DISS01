package org.crawl.services;

import org.crawl.SocialStreamParserApp;
import org.crawl.entity.DBStatus;
import org.crawl.repositories.DBStatusRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import twitter4j.Status;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

@Service
public class AxivCrawlerServiceImpl implements AxivCrawlerService {
    public static final String PDF = ".pdf";
    public static final String PROTOCOL_SEPARATOR = "://";
    public static final String unwantedCharacters = "[\\\\/:*?\"<>|]";

    @Autowired
    private DBStatusRepository dbStatusRepository;

    Logger log = LoggerFactory.getLogger(AxivCrawlerServiceImpl.class);

    public void crawl(Status status, String expandedURL, String targetPath) {
        try {
            if (statusWasNotProcessed(status)) {
                log.warn("Skipping status " + status.getId() + " status was already processed!");
                return;
            }

            Document doc = Jsoup.connect(expandedURL).get();
            if (!doc.location().contains("arxiv.org")) {
                log.warn("Skipping status, it is not axiv link!");
                return;
            }

            String filename = getFilename(doc, doc.title().lastIndexOf(']') + 1);

            Optional<DBStatus> databaseStatus = dbStatusRepository.findById(filename);
            final String pathname = targetPath + filename + PDF;


            if (databaseStatus.isPresent()) {
                log.warn("File " + pathname + " already exist!");
                return;
            }



            doc.select("div.full-text a").forEach(link -> {
                String href = link.attr("href");
                if (href.contains("/pdf/")) {
                    try {
                        URL baseURL = new URL(doc.baseUri());
                        final URL pdfURL = new URL(baseURL.getProtocol() + PROTOCOL_SEPARATOR + baseURL.getHost() + href);
                        processFile(pdfURL, pathname, status);
                        DBStatus dbStatus = new DBStatus(filename, status.getId(),  baseURL.toString(), status.getUser().getName(), status.getCreatedAt());
                        dbStatusRepository.save(dbStatus);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }

                }
            });


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getFilename(Document doc, int lastIndexOfDescription) {
        return doc.title().substring(lastIndexOfDescription, Math.min(doc.title().length() - 1, lastIndexOfDescription + 80))
                .replaceAll(unwantedCharacters, "").trim();
    }

    private boolean statusWasNotProcessed(Status status) {
        return dbStatusRepository.findByStatusID(status.getId()).isPresent();
    }

    private void processFile(URL pdfUrl, String pathname, Status status) {
        try {
            log.debug("Storing file " + pathname);
            log.debug("From user " + status.getUser());
            log.debug("Created at " + status.getCreatedAt());
            log.debug("With status " + status.getId());

            byte[] ba1 = new byte[1024];
            int baLength;
            FileOutputStream fos1 = new FileOutputStream(pathname);
            InputStream is1 = pdfUrl.openStream();
            while ((baLength = is1.read(ba1)) != -1) {
                fos1.write(ba1, 0, baLength);
            }
            fos1.flush();
            fos1.close();
            is1.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
