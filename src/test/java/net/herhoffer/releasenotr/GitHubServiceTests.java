package net.herhoffer.releasenotr;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.hamcrest.Matchers;
import org.hamcrest.collection.IsCollectionWithSize;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.kohsuke.github.GHCommit;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

@QuarkusTest
@Tag("integration")
class GitHubServiceTests
{
	@Inject
	GitHubService gitHubService;

	@Test
	void shouldListCommits() throws IOException
	{
		// given
		String repo = "neuland-ingolstadt/reimanns-scraper";

		// when
		List<GHCommit> commits = gitHubService.getCommits(repo, "main");

		// then
		assertThat(commits, hasSize(greaterThanOrEqualTo(10)));
	}

	@Test
	void shouldGetReadme() throws IOException
	{
		// given
		String repo = "neuland-ingolstadt/reimanns-scraper";

		// when
		String readme = gitHubService.getReadme(repo);

		// then
		assertThat(readme, containsString("This process provides a GraphQL API for Reimann's weekly menu"));
	}
}
