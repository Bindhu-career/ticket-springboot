package com.bigbang.api.mail;

import com.sendgrid.*;
import java.io.IOException;

import org.apache.http.impl.execchain.MainClientExec;

public class SendGridMail {
	public static void sendMail(String toMail, String code) throws IOException {
		Email from = new Email("mgmt.elitelance@gmail.com");
		String subject = "Elitelance Signup Confirmation";
		Email to = new Email(toMail);
		Content content = new Content("text/html", MailTemplates.otpTemplateStart+code+MailTemplates.otpTemplateEnd);
		Mail mail = new Mail(from, subject, to, content);

		SendGrid sg = new SendGrid(MailTemplates.sendGridApi);
		Request request = new Request();
		try {
			request.setMethod(Method.POST);
			request.setEndpoint("mail/send");
			request.setBody(mail.build());
			Response response = sg.api(request);
//			System.out.println(response.getStatusCode());
//			System.out.println(response.getBody());
//			System.out.println(response.getHeaders());
		} catch (IOException ex) {
			throw ex;
		}
	}

	public static void forgotPasswordMail(String toMail) throws IOException {
		

		Email from = new Email("mgmt.elitelance@gmail.com");
		String subject = "Elitelance Forgot Password Confirmation";

		Email to = new Email(toMail);
		Content content = new Content("text/html", MailTemplates.forgotTemplate);
		Mail mail = new Mail(from, subject, to, content);
		

		SendGrid sg = new SendGrid(MailTemplates.sendGridApi);
		Request request = new Request();
		try {
			request.setMethod(Method.POST);
			request.setEndpoint("mail/send");
			request.setBody(mail.build());
			Response response = sg.api(request);
		} catch (IOException ex) {
			throw ex;
		}
	}
	
	public static void welcomeTemplate(String toMail) throws IOException {
		

		Email from = new Email("mgmt.elitelance@gmail.com");
		String subject = "Welcome to Elitelance";

		Email to = new Email(toMail);
		Content content = new Content("text/html", MailTemplates.welcomeTemplate);
		Mail mail = new Mail(from, subject, to, content);
		

		SendGrid sg = new SendGrid(MailTemplates.sendGridApi);
		Request request = new Request();
		try {
			request.setMethod(Method.POST);
			request.setEndpoint("mail/send");
			request.setBody(mail.build());
			Response response = sg.api(request);
		} catch (IOException ex) {
			throw ex;
		}
	}
	
public static void deactivateTemplate(String toMail) throws IOException {
		

		Email from = new Email("mgmt.elitelance@gmail.com");
		String subject = "Elitelance Deactivated";

		Email to = new Email(toMail);
		Content content = new Content("text/html", MailTemplates.deactivateTemplate);
		Mail mail = new Mail(from, subject, to, content);
		

		SendGrid sg = new SendGrid(MailTemplates.sendGridApi);
		Request request = new Request();
		try {
			request.setMethod(Method.POST);
			request.setEndpoint("mail/send");
			request.setBody(mail.build());
			Response response = sg.api(request);
		} catch (IOException ex) {
			throw ex;
		}
	}
}
