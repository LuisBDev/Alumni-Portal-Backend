package com.alumniportal.unmsm.util;

import com.alumniportal.unmsm.dto.*;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.File;

import org.apache.pdfbox.pdmodel.font.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class CVGenerator {

    public static File generateCV(UserCVDTO cv) throws IOException {
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

        // Header (Name and Contact Info)
        String fullName = cv.getName() + " " + cv.getPaternalSurname() + " " + cv.getMaternalSurname();
        String contactInfo = cv.getContactNumber() + " | " + cv.getEmail();

        // Centering Name
        contentStream.setFont(boldFont, 18);
        float titleWidth = boldFont.getStringWidth(fullName) / 1000 * 18; // Text width (PDFBox returns in 1000ths of a point)
        float titleX = (pageWidth - titleWidth) / 2; // Center X position

        contentStream.newLineAtOffset(titleX, 750);
        contentStream.showText(fullName);
        contentStream.newLine();

        // Centering Contact Info
        contentStream.setFont(regularFont, 12);
        float contactWidth = regularFont.getStringWidth(contactInfo) / 1000 * 12; // Text width for contact info
        float contactX = (pageWidth - contactWidth) / 2; // Center X position

        contentStream.newLineAtOffset(contactX - titleX, 0); // Adjust offset since we're already at the center
        contentStream.showText(contactInfo);
        contentStream.newLine();
        contentStream.newLine();

        // Reset offset for other sections to be left-aligned at left margin
        contentStream.newLineAtOffset(-contactX + leftMargin, 0); // Move to the left margin for other content

        // About Section
        contentStream.setFont(boldFont, 14);
        contentStream.showText("About");
        contentStream.newLine();
        contentStream.setFont(regularFont, 12);
        contentStream.showText(cv.getAbout());
        contentStream.newLine();
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
            contentStream.showText(work.getDescription());
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
            contentStream.showText(edu.getInstitution());
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
            contentStream.showText(proj.getDescription());
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

//       Skills Section
        contentStream.setFont(boldFont, 14);
        contentStream.showText("Skills");
        contentStream.newLine();
        contentStream.setFont(regularFont, 12);
        for (SkillDTO skill : cv.getSkills()) {
            contentStream.showText(skill.getName() + " | " + skill.getLevel());
            contentStream.newLine();
        }
        contentStream.newLine();


        contentStream.endText();
        contentStream.close();

        // Save PDF
        File pdfFile = new File("UserCV_" + cv.getName() + ".pdf");
        document.save(pdfFile);
        document.close();

        return pdfFile;
    }

    // Helper method to safely format nullable LocalDate objects
    private static String formatNullableLocalDate(LocalDate date, DateTimeFormatter dateFormat) {
        if (date != null) {
            return date.format(dateFormat);
        } else {
            return "N/A"; // Return default if LocalDate is null
        }
    }

}
