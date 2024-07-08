# gardenRepository

Der Gardening Planner ist eine Webanwendung, die Nutzern dabei hilft, ihre Pflanzen zu verwalten und die Pflege zu planen, einschließlich der Verfolgung von Bewässerungsplänen und Düngebedarf. 

Die Anwendung verwendet Java 22, Spring Boot, Spring Security, Thymeleaf, H2-Datenbank und Maven. Um das Projekt einzurichten und zu starten, stellen Sie sicher, dass Java 22 und Maven installiert sind. Notwendige Dependencies werden durch Maven automatisch installiert.

Die Anwendung bietet eine Benutzerauthentifizierung und Registrierung. Nach dem Einloggen werden die Benutzer zur Startseite weitergeleitet, die einen Kalender mit dem aktuellen Monat anzeigt und Icons für Pflanzpflegeaufgaben enthält. Benutzer können über diese Icons fahren, um Details zu sehen und zwischen den Monaten navigieren, um im Voraus zu planen. Auf der Seite „Pflanzen hinzufügen“ können Benutzer Pflanzen zu ihrer persönlichen Liste hinzufügen, während auf der Seite „Meine Pflanzen“ die Pflanzen des Benutzers mit erweiterbaren Details, einschließlich Pflanzeninformationen, Spitznamen und Notizen, angezeigt werden. 

Die Projektstruktur umfasst Authentifizierung, Konfiguration, Controller, Repositories, Entitätsklassen, Hilfsklassen, statische Ressourcen und Thymeleaf-Vorlagen. Die Anwendung verwendet eine In-Memory-H2-Datenbank mit Standarddaten, die für den Produktionseinsatz angepasst werden sollten.
