package commons.tool.utils.regex;

public class SimpleStringReplacementImpl implements IRegexReplaceRule
{
	public String replacement;

	public SimpleStringReplacementImpl(String replacement)
	{
		this.replacement = replacement;
	}

	@Override
	public String getReplacement(String matchedGroup)
	{
		return replacement.toString();
	}

}
