/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.neotronix.utility;

import javax.mail.MessagingException;

/**
 *
 * @author ASUS
 */
public class MailMessage {

    public static void registrationSuccess(String recipientMailId, String name) throws MessagingException {
        String subject = "Registration Successful";
        String htmlTextMessage = "" + "<html>" + "<body>"
                + "<h2 style='color:green;'>Welcome to " + AppInfo.appName + "</h2>" + "" + "Hi " + name + ","
                + "<br><br>Thanks for singing up with " + AppInfo.appName + ".<br>"
                + "We are glad that you choose us. We invite you to check out our latest collection of new electonics appliances."
                + "<br>We are providing upto 60% OFF on most of the electronic gadgets. So please visit our site and explore the collections."
                + "<br><br>Our Online electronics is growing in a larger amount these days and we are in high demand so we thanks all of you for "
                + "making us up to that level. We Deliver Product to your house with no extra delivery charges and we also have collection of most of the"
                + "branded items.<br><br>As a Welcome gift for our New Customers we are providing additional 10% OFF Upto 500 Rs for the first product purchase. "
                + "<br>To avail this offer you only have "
                + "to enter the promo code given below.<br><br><br> PROMO CODE: " + "" + AppInfo.appName.toUpperCase() + "500<br><br><br>"
                + "Have a good day!<br>" + "" + "</body>" + "</html>";
        JavaMailUtil.sendMail(recipientMailId, subject, htmlTextMessage);
    }

    public static void transactionSuccess(String recipientMail, String name, String transId, double transAmount) throws MessagingException {
        String subject = "Order Placed Successfully";
        String htmlTextMessage = "" + "<html>" + "<body>"
                + "<h2 style='color:green;'>Welcome to " + AppInfo.appName + "</h2>" + "" + "Hi " + name + ","
                + "<br><br>We are pleased to inform you that your order was placed successfully.<br>"
                + "<table style='border-collapse: collapse; width: 100%; max-width: 600px;' border='1'>"
                + "<tr>"
                + "<th style='padding: 8px; text-align: left;'>Transaction ID</th>"
                + "<td style='padding: 8px;'>" + transId + "</td>"
                + "</tr>"
                + "<tr>"
                + "<th style='padding: 8px; text-align: left;'>Transaction Amount</th>"
                + "<td style='padding: 8px;'>₹" + String.format("%.2f", transAmount) + "</td>"
                + "</tr>"
                + "</table>"
                + "<br>"
                + "<p>Thank you for shopping with us! We hope you enjoy your purchase. If you have any questions or need assistance, please don't hesitate to contact us.</p>"
                + "<br>"
                + "<p>Have a great day!</p>"
                + "<br>"
                + "<p>Best Regards,</p>"
                + "<p>The " + AppInfo.appName + " Team</p>"
                + "</body>"
                + "</html>";
        JavaMailUtil.sendMail(recipientMail, subject, htmlTextMessage);
    }

    public static void shipmentSuccess(String recipientMail, String name, String address) throws MessagingException {
        String subject = "Shipment Successful";
        String htmlTextMessage = ""
                + "<html>"
                + "<body>"
                + "<h2 style='color:green;'>Your Order Has Been Shipped!</h2>"
                + "<p>Hi " + name + ",</p>"
                + "<p>We are excited to let you know that your order has been successfully shipped to the following address:</p>"
                + "<p style='border: 1px solid #ddd; padding: 10px; background-color: #f9f9f9;'>"
                + address
                + "</p>"
                + "<p>You can expect your delivery soon. Thank you for choosing " + AppInfo.appName + " for your shopping needs!</p>"
                + "<p>If you have any questions about your order or shipment, feel free to contact our support team.</p>"
                + "<br>"
                + "<p>Best Regards,</p>"
                + "<p>The " + AppInfo.appName + " Team</p>"
                + "</body>"
                + "</html>";
        JavaMailUtil.sendMail(recipientMail, subject, htmlTextMessage);
    }

    public static void stockUpdate(String recipientMail, String name, String prodId) throws MessagingException {
        String subject = "Product Back in Stock!";
        String htmlTextMessage = ""
                + "<html>"
                + "<body>"
                + "<h2 style='color:green;'>Your Requested Product is Back in Stock!</h2>"
                + "<p>Hi " + name + ",</p>"
                + "<p>We are excited to inform you that the product you requested is now back in stock and available for order.</p>"
                + "<table style='border-collapse: collapse; width: 100%; max-width: 600px;' border='1'>"
                + "<tr>"
                + "<th style='padding: 8px; text-align: left;'>Product ID</th>"
                + "<td style='padding: 8px;'>" + prodId + "</td>"
                + "</tr>"
                + "</table>"
                + "<br>"
                + "<p>Thank you for choosing " + AppInfo.appName + ". We look forward to serving you!</p>"
                + "<br>"
                + "<p>Best Regards,</p>"
                + "<p>The " + AppInfo.appName + " Team</p>"
                + "</body>"
                + "</html>";
        JavaMailUtil.sendMail(recipientMail, subject, htmlTextMessage);
    }
}
