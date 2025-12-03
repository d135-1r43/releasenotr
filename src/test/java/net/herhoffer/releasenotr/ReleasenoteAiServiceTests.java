package net.herhoffer.releasenotr;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.kohsuke.github.GHCommit;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.wildfly.common.Assert.assertNotNull;

@QuarkusTest
@Tag("integration")
class ReleasenoteAiServiceTests
{
	@Inject
	GitHubService gitHubService;

	@Inject
	ReleasenoteAiService releasenoteAiService;

	@Test
	void shouldGenerateReleaseNotes() throws IOException
	{
		// given
		String repo = "neuland-ingolstadt/reimanns-scraper";
		List<GHCommit> commits = gitHubService.getCommits(repo, "main");
		String readme = gitHubService.getReadme(repo);

		// when
		ReleaseNotes notes = releasenoteAiService.generateReleaseNotes(commits, readme);

		// then
		assertNotNull(notes);
		assertThat(notes.mdText.length(), greaterThan(100));
	}
}
