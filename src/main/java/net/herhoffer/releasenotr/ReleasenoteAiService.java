package net.herhoffer.releasenotr;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.RegisterAiService;
import jakarta.enterprise.context.ApplicationScoped;
import org.kohsuke.github.GHCommit;

import java.util.List;

@RegisterAiService
@SystemMessage("You are a product manager writing release notes for a piece of software.")
@ApplicationScoped
public interface ReleasenoteAiService
{
	@UserMessage("""
		    Use the commits {commits} and the Readme.md {readme}
		""")
	ReleaseNotes generateReleaseNotes(List<GHCommit> commits, String readme);
}
