package net.herhoffer.releasenotr;

import dev.langchain4j.model.output.structured.Description;

@Description("Release notes for a piece of software.")
public class ReleaseNotes
{
	@Description("A ca. two paragraph text about the release, as it would be used in e.g. iOS Store or Playstore")
	String prosaicText;

	@Description("A MD text with itemized changes inteded for the user of the software")
	String mdText;
}
