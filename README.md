# releasenotr

Demo-Projekt für den Neuland Cookie Talk am 5.12.2025.

Eine Quarkus-Anwendung, die KI-gestützte Release Notes aus GitHub-Commits generiert.

## Technologien

- **Quarkus** (Java 21) - Supersonic Subatomic Java Framework
- **LangChain4j mit OpenAI** - KI-gestützte Textgenerierung
- **hub4j GitHub API** - Zugriff auf GitHub-Repositories
- **Qute** - Template-Engine für die Web-Oberfläche

## Konfiguration

Erforderliche Umgebungsvariablen oder Einträge in `application.properties`:

- `gh.user` - GitHub-Benutzername
- `gh.pat` - GitHub Personal Access Token

## Anwendung starten

Entwicklungsmodus mit Live-Reload:

```shell
./mvnw quarkus:dev
```

Die Anwendung ist dann unter http://localhost:8080 erreichbar.

## Tests ausführen

```shell
./mvnw test
```

## Anwendung paketieren

```shell
./mvnw package
```

Die ausführbare JAR-Datei befindet sich anschließend unter `target/quarkus-app/quarkus-run.jar`.

## Weiterführende Links

- [Quarkus](https://quarkus.io/)
- [LangChain4j Quarkus Extension](https://docs.quarkiverse.io/quarkus-langchain4j/dev/index.html)
- [GitHub API (hub4j)](https://hub4j.github.io/github-api/)