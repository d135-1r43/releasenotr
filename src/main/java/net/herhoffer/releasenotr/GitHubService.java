package net.herhoffer.releasenotr;

import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.kohsuke.github.GHCommit;
import org.kohsuke.github.GHContent;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

@ApplicationScoped
public class GitHubService
{
	@ConfigProperty(name = "gh.user")
	String ghUser;

	@ConfigProperty(name = "gh.pat")
	String ghPat;

	public List<GHCommit> getCommits(String repo, String ref) throws IOException
	{
		GitHub gitHub = GitHub.connect(ghUser, ghPat);

		GHRepository repository = gitHub.getRepository(repo);
		return repository.queryCommits()
			.from(ref)
			.list()
			.toList();
	}

	public String getReadme(String repo) throws IOException
	{
		GitHub gitHub = GitHub.connect(ghUser, ghPat);

		GHRepository repository = gitHub.getRepository(repo);
		GHContent readme = repository.getReadme();
		try (InputStream stream = readme.read())
		{
			return new String(stream.readAllBytes(), StandardCharsets.UTF_8);
		}
	}
}
