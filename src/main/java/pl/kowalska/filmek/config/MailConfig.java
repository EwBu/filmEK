package pl.kowalska.filmek.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {

    @Bean(name = "javaMailSender")
    public JavaMailSender javaMailSender(@Value("${spring.mail.host}") String smtpHost,
                                         @Value("${spring.mail.port}") int smtpPort,
                                         @Value("${spring.mail.username}") String mailName,
                                         @Value("${spring.mail.password}") String mailCred) {
        Properties props = new Properties();
//        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable","true");
        props.put("mail.smtp.starttls.required ","true");
        props.put("mail.smtp.ssl.trust","smtp.gmail.com");

        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(smtpHost);
        javaMailSender.setPort(smtpPort);
        javaMailSender.setUsername(mailName);
        javaMailSender.setPassword(mailCred);
        javaMailSender.setJavaMailProperties(props);
        return javaMailSender;
    }
}
