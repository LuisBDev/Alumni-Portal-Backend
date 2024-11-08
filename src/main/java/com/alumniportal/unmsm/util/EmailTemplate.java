package com.alumniportal.unmsm.util;


import org.springframework.stereotype.Service;


@Service
public class EmailTemplate {
    public static String generateHtmlContent(String name, String activityTitle, String description, String eventType,
                                             String startDate, String endDate, String location, boolean isEnrollable) {
        return """
                    <!DOCTYPE html>
                    <html>
                    <head>
                        <meta charset="UTF-8">
                        <style>
                            body { 
                                font-family: Arial, sans-serif;
                                line-height: 1.6;
                                color: #333;
                            }
                            .container {
                                max-width: 600px;
                                margin: 0 auto;
                                padding: 20px;
                                background-color: #f9f9f9;
                            }
                            .header {
                                background-color: #4CAF50;
                                color: white;
                                padding: 10px;
                                text-align: center;
                                border-radius: 5px;
                            }
                            .content {
                                background-color: white;
                                padding: 20px;
                                margin-top: 20px;
                                border-radius: 5px;
                                box-shadow: 0 2px 5px rgba(0,0,0,0.1);
                            }
                            .details {
                                margin: 15px 0;
                            }
                            .detail-item {
                                margin: 10px 0;
                            }
                            .company-info {
                                background-color: #f5f5f5;
                                padding: 10px;
                                margin: 15px 0;
                                border-radius: 5px;
                            }
                            .footer {
                                text-align: center;
                                margin-top: 20px;
                                padding: 10px;
                                color: #666;
                            }
                        </style>
                    </head>
                    <body>
                        <div class="container">
                            <div class="header">
                                <h1>Nueva Actividad Publicada</h1>
                            </div>
                            <div class="content">
                                <div class="company-info">
                                    <strong>Publicado por:</strong> %s
                                </div>
                                <h2>%s</h2>
                                <div class="details">
                                    <div class="detail-item">
                                        <strong>Descripción:</strong>
                                        <p>%s</p>
                                    </div>
                                    <div class="detail-item">
                                        <strong>Tipo de evento:</strong> %s
                                    </div>
                                    <div class="detail-item">
                                        <strong>Fecha de inicio:</strong> %s
                                    </div>
                                    <div class="detail-item">
                                        <strong>Fecha de fin:</strong> %s
                                    </div>
                                    <div class="detail-item">
                                        <strong>Ubicación:</strong> %s
                                    </div>
                                    <div class="detail-item">
                                        <strong>Inscripción disponible:</strong> %s
                                    </div>
                                </div>
                            </div>
                            <div class="footer">
                                <p>Este es un correo automático, por favor no responder.</p>
                            </div>
                        </div>
                    </body>
                    </html>
                """.formatted(
                name,
                activityTitle,
                description,
                eventType,
                startDate,
                endDate,
                location,
                isEnrollable ? "Sí" : "No"
        );
    }

    public static String generateHtmlContentEnrollCreated(String activityTitle, String description, String eventType,
                                                          String startDate, String endDate, String location, boolean isEnrollable, String enrollmentDate, String status) {
        return """
                    <!DOCTYPE html>
                    <html>
                    <head>
                        <meta charset="UTF-8">
                        <style>
                            body { 
                                font-family: Arial, sans-serif;
                                line-height: 1.6;
                                color: #333;
                            }
                            .container {
                                max-width: 600px;
                                margin: 0 auto;
                                padding: 20px;
                                background-color: #f9f9f9;
                            }
                            .header {
                                background-color: #4CAF50;
                                color: white;
                                padding: 10px;
                                text-align: center;
                                border-radius: 5px;
                            }
                            .content {
                                background-color: white;
                                padding: 20px;
                                margin-top: 20px;
                                border-radius: 5px;
                                box-shadow: 0 2px 5px rgba(0,0,0,0.1);
                            }
                            .details {
                                margin: 15px 0;
                            }
                            .detail-item {
                                margin: 10px 0;
                            }
                            .company-info {
                                background-color: #f5f5f5;
                                padding: 10px;
                                margin: 15px 0;
                                border-radius: 5px;
                            }
                            .footer {
                                text-align: center;
                                margin-top: 20px;
                                padding: 10px;
                                color: #666;
                            }
                        </style>
                    </head>
                    <body>
                        <div class="container">
                            <div class="header">
                                <h1>Confirmación de Inscripción en Actividad</h1>
                            </div>
                            <div class="content">
                
                                <h2>%s</h2>
                                <div class="details">
                                    <div class="detail-item">
                                        <strong>Descripción:</strong>
                                        <p>%s</p>
                                    </div>
                                    <div class="detail-item">
                                        <strong>Tipo de evento:</strong> %s
                                    </div>
                                    <div class="detail-item">
                                        <strong>Fecha de inicio:</strong> %s
                                    </div>
                                    <div class="detail-item">
                                        <strong>Fecha de fin:</strong> %s
                                    </div
                                    <div class="detail-item">
                                        <strong>Ubicación:</strong> %s
                                    </div>
                                    <div class="detail-item">
                                        <strong>Inscripción disponible:</strong> %s
                                    </div>
                                    <div class="detail-item">
                                        <strong>Fecha de inscripción:</strong> %s
                                    </div>
                                    <div class="detail-item">
                                        <strong>Estado:</strong> %s
                                    </div>
                                </div>
                            </div>
                            <div class="footer">
                                <p>Este es un correo automático, por favor no responder.</p>
                            </div>
                        </div>
                    </body>
                    </html>
                """.formatted(
                activityTitle,
                description,
                eventType,
                startDate,
                endDate,
                location,
                isEnrollable ? "Sí" : "No",
                enrollmentDate,
                status
        );

    }

}