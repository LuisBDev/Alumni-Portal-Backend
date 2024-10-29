package com.alumniportal.unmsm.util;

import com.alumniportal.unmsm.dto.*;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.pdmodel.font.PDFont;

public class CVGenerator {

    public static byte[] generateCV(UserCVDTO cv) throws IOException {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        // Set up fonts
        PDFont boldFont = PDType1Font.HELVETICA_BOLD;
        PDFont regularFont = PDType1Font.HELVETICA;
        PDFont italicFont = PDType1Font.HELVETICA_OBLIQUE;
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MMM yyyy");

        contentStream.beginText();
        contentStream.setLeading(16f);

        // Page dimensions (for centering)
        float pageWidth = page.getMediaBox().getWidth();
        float leftMargin = 50f; // Standard left margin
        float maxWidth = pageWidth - leftMargin * 2; // Max width for the text

        // Header (Name and Contact Info)
        String fullName = (cv.getName() != null ? cv.getName() : "") + " " +
                (cv.getPaternalSurname() != null ? cv.getPaternalSurname() : "") + " " +
                (cv.getMaternalSurname() != null ? cv.getMaternalSurname() : "");
        String contactInfo = (cv.getContactNumber() != null ? cv.getContactNumber() : "") + " | " +
                (cv.getEmail() != null ? cv.getEmail() : "");


        // Centering Name
        contentStream.setFont(boldFont, 18);
        float titleWidth = boldFont.getStringWidth(fullName) / 1000 * 18;
        float titleX = (pageWidth - titleWidth) / 2;

        contentStream.newLineAtOffset(titleX, 750);
        contentStream.showText(fullName);
        contentStream.newLine();

        // Centering Contact Info
        contentStream.setFont(regularFont, 12);
        float contactWidth = regularFont.getStringWidth(contactInfo) / 1000 * 12;
        float contactX = (pageWidth - contactWidth) / 2;

        contentStream.newLineAtOffset(contactX - titleX, 0);
        contentStream.showText(contactInfo);
        contentStream.newLine();
        contentStream.newLine();

        // Reset offset for other sections to be left-aligned at left margin
        contentStream.newLineAtOffset(-contactX + leftMargin, 0);

        // About Section
        contentStream.setFont(boldFont, 14);
        contentStream.showText("About");
        contentStream.newLine();
        contentStream.setFont(regularFont, 12);
        for (String line : splitTextIntoLines(cv.getAbout() != null ? cv.getAbout() : "", maxWidth, regularFont, 12)) {
            contentStream.showText(line);
            contentStream.newLine();
        }
        contentStream.newLine();

        // Work Experience Section
        contentStream.setFont(boldFont, 14);
        contentStream.showText("Work Experience");
        contentStream.newLine();
        contentStream.setFont(regularFont, 12);
        for (WorkExperienceDTO work : cv.getWorkExperience()) {
            String startDate = formatNullableLocalDate(work.getStartDate(), dateFormat);
            String endDate = (work.getEndDate() != null) ? formatNullableLocalDate(work.getEndDate(), dateFormat) : "Presente";

            contentStream.showText(work.getCompany() + " | " + work.getJobTitle() + " - " + startDate + " a " + endDate);
            contentStream.newLine();
            contentStream.setFont(italicFont, 12);
            for (String line : splitTextIntoLines(work.getDescription(), maxWidth, italicFont, 12)) {
                contentStream.showText(line);
                contentStream.newLine();
            }
            contentStream.setFont(regularFont, 12);
            contentStream.newLine();
        }
        contentStream.newLine();

        // Education Section
        contentStream.setFont(boldFont, 14);
        contentStream.showText("Education");
        contentStream.newLine();
        contentStream.setFont(regularFont, 12);
        for (EducationDTO edu : cv.getEducation()) {
            String startDate = formatNullableLocalDate(edu.getStartDate(), dateFormat);
            String endDate = formatNullableLocalDate(edu.getEndDate(), dateFormat);

            contentStream.showText(edu.getDegree() + " | " + edu.getFieldOfStudy() + " - " + startDate + " a " + endDate);
            contentStream.newLine();
            contentStream.setFont(italicFont, 12);
            for (String line : splitTextIntoLines(edu.getInstitution(), maxWidth, italicFont, 12)) {
                contentStream.showText(line);
                contentStream.newLine();
            }
            contentStream.setFont(regularFont, 12);
            contentStream.newLine();
        }
        contentStream.newLine();

        // Projects Section
        contentStream.setFont(boldFont, 14);
        contentStream.showText("Projects");
        contentStream.newLine();
        contentStream.setFont(regularFont, 12);
        for (ProjectDTO proj : cv.getProjects()) {
            String projectDate = formatNullableLocalDate(proj.getDate(), dateFormat);
            contentStream.showText(proj.getName() + " - " + projectDate);
            contentStream.newLine();
            contentStream.setFont(italicFont, 12);
            for (String line : splitTextIntoLines(proj.getDescription(), maxWidth, italicFont, 12)) {
                contentStream.showText(line);
                contentStream.newLine();
            }
            contentStream.setFont(regularFont, 12);
            contentStream.newLine();
        }
        contentStream.newLine();

        // Certifications Section
        contentStream.setFont(boldFont, 14);
        contentStream.showText("Certifications");
        contentStream.newLine();
        contentStream.setFont(regularFont, 12);
        for (CertificationDTO cert : cv.getCertifications()) {
            String issueDate = formatNullableLocalDate(cert.getIssueDate(), dateFormat);
            contentStream.showText(cert.getName() + " | " + cert.getIssuingOrganization() + " - " + issueDate);
            contentStream.newLine();
        }
        contentStream.newLine();

        // Skills Section
        contentStream.setFont(boldFont, 14);
        contentStream.showText("Skills");
        contentStream.newLine();
        contentStream.setFont(regularFont, 12);
        for (SkillDTO skill : cv.getSkills()) {
            for (String line : splitTextIntoLines(skill.getName() + " | " + skill.getLevel(), maxWidth, regularFont, 12)) {
                contentStream.showText(line);
                contentStream.newLine();
            }
        }
        contentStream.newLine();

        contentStream.endText();
        contentStream.close();

        // Use ByteArrayOutputStream instead of saving to file
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        document.save(byteArrayOutputStream);
        document.close();

        return byteArrayOutputStream.toByteArray();
    }

    private static String formatNullableLocalDate(LocalDate date, DateTimeFormatter dateFormat) {
        if (date != null) {
            return date.format(dateFormat);
        } else {
            return "N/A";
        }
    }

    private static List<String> splitTextIntoLines(String text, float maxWidth, PDFont font, float fontSize) throws IOException {
        List<String> lines = new ArrayList<>();
        String[] words = text.split(" ");
        StringBuilder currentLine = new StringBuilder();

        for (String word : words) {
            String testLine = currentLine.length() == 0 ? word : currentLine + " " + word;
            float textWidth = font.getStringWidth(testLine) / 1000 * fontSize;

            if (textWidth > maxWidth) {
                lines.add(currentLine.toString());
                currentLine = new StringBuilder(word);
            } else {
                currentLine.append(currentLine.length() == 0 ? word : " " + word);
            }
        }
        lines.add(currentLine.toString());

        return lines;
    }
}

