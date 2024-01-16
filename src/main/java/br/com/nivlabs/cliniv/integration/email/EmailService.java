package br.com.nivlabs.cliniv.integration.email;

import java.util.Date;
import java.util.Map;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import br.com.nivlabs.cliniv.service.BaseService;

/**
 * Servico de integração de e-mail
 * 
 * @author viniciosarodrigues
 *
 */
@Service
public class EmailService implements BaseService {

    @Value("${nivlabs.integration.email.default-sender}")
    private String sender;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private JavaMailSender javaMailSender;

    /**
     * Envia uma mensagem HTML via e-mail
     * 
     * @param message Mensagem em HTML
     */
    public void sendHtmlMessage(MimeMessage mimeMessage) {
        javaMailSender.send(mimeMessage);
    }

    /**
     * Envia uma mensagem simples via e-mail
     * 
     * @param subject Assunto da mensagem
     * @param to E-mail que receberá a mensagem
     * @param text Texto que será enviado por e-mail
     */
    public void sendSimpleMessage(String subject, String to, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(sender);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        javaMailSender.send(message);
    }

    /**
     * Prepara a mensagem em HTML para envio
     * 
     * @param subject Assunto da mensagem de e-mail
     * @param template Template HTML para envio
     * @param variables Variáveis para processamento no template
     * @param to E-mail que receberá a mensagem
     * @return Mensagem processada pronta para envio
     * @throws MessagingException
     */
    public MimeMessage prepareHtmlMessage(String subject, String template, Map<String, Object> variables, String to)
            throws MessagingException {
        MimeMessage mm = javaMailSender.createMimeMessage();
        MimeMessageHelper mmh = new MimeMessageHelper(mm, true);

        Context context = new Context();
        if (variables != null) {
            for (var entry : variables.entrySet()) {
                context.setVariable(entry.getKey(), entry.getValue());
            }
        }
        mmh.setTo(to);
        mmh.setFrom(sender);
        mmh.setSubject(subject);
        mmh.setSentDate(new Date(System.currentTimeMillis()));
        mmh.setText(templateEngine.process(template, context), true);

        return mmh.getMimeMessage();
    }
}
