package net.herhoffer.releasenotr;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import org.kohsuke.github.GHCommit;

import java.io.IOException;
import java.util.List;

import static java.util.Objects.requireNonNull;

@Path("/releasenotes")
public class ReleaseNotesResource
{
	private final Template releasenotes;

	@Inject
	GitHubService gitHubService;

	@Inject
	ReleasenoteAiService releasenoteAiService;

	public ReleaseNotesResource(Template releasenotes)
	{
		this.releasenotes = requireNonNull(releasenotes, "releasenotes template is required");
	}

	@GET
	@Produces(MediaType.TEXT_HTML)
	public TemplateInstance get(@QueryParam("repo") String repo)
	{
		return releasenotes.data("repo", repo);
	}

	@GET
	@Path("/commits")
	@Produces(MediaType.APPLICATION_JSON)
	public List<CommitInfo> getCommits(@QueryParam("repo") String repo) throws IOException
	{
		List<GHCommit> commits = gitHubService.getCommits(repo, "main");
		return commits.stream()
			.map(this::toCommitInfo)
			.toList();
	}

	@POST
	@Path("/generate")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ReleaseNotes generate(GenerateRequest request) throws IOException
	{
		List<GHCommit> allCommits = gitHubService.getCommits(request.repo(), "main");

		List<GHCommit> selectedCommits = allCommits.stream()
			.filter(c -> request.commitShas().contains(getSha(c)))
			.toList();

		String readme = gitHubService.getReadme(request.repo());

		return releasenoteAiService.generateReleaseNotes(selectedCommits, readme);
	}

	private CommitInfo toCommitInfo(GHCommit commit)
	{
		try
		{
			return new CommitInfo(
				commit.getSHA1(),
				commit.getCommitShortInfo().getMessage(),
				commit.getCommitDate().getTime()
			);
		}
		catch (IOException e)
		{
			throw new RuntimeException("Failed to get commit info", e);
		}
	}

	private String getSha(GHCommit commit)
	{
		return commit.getSHA1();
	}

	public record CommitInfo(String sha, String message, long date) {}

	public record GenerateRequest(String repo, List<String> commitShas) {}
}