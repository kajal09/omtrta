package com.kajalbordhon.util;

import java.io.IOException;
import java.io.OutputStream;
import static java.nio.file.Files.newOutputStream;
import java.nio.file.Path;
import static java.nio.file.Paths.get;
import java.util.Properties;

/**
 * Writes and reads the properties
 *
 * @author kajal
 */
public class PropertiesManager {

    public final void writeTextProperties(final String path, final String propFileName,
            final String name, final String email, final String smtpServer,
            final String imapServer, final String dataName,
            final String dataUrl, final String smtpPort,
            final String imapPort, final String dataPort,
            final String dataUser, final String emailPassword, final String dataPass) throws IOException {

        Properties prop = new Properties();

        prop.setProperty("name", name);
        prop.setProperty("email", email);
        prop.setProperty("smtpServer", smtpServer);
        prop.setProperty("imapServer", imapServer);
        prop.setProperty("dataName", dataName);
        prop.setProperty("dataUrl", dataUrl);
        prop.setProperty("smtpPort", smtpPort);
        prop.setProperty("imapPort", imapPort);
        prop.setProperty("dataPort", dataPort);
        prop.setProperty("dataUser", dataUser);
        prop.setProperty("emailPassword", emailPassword);
        prop.setProperty("dataPass", dataPass);

        Path txtFile = get(path, propFileName + ".properties");

        // Creates the file or if file exists it is truncated to length of zero
        // before writing
        try (OutputStream propFileStream = newOutputStream(txtFile)) {
            prop.store(propFileStream, "SMTP Properties");
        }
    }

}
