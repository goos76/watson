package twitter4j;

import org.junit.Test;

import twitter4j.auth.AccessToken;

public class TwitterScrapeTest {
	private static final AccessToken ACCESS_TOKEN = new AccessToken(
			"78873076-3RTKJgR7nCQLWfjPkJ0rmZqYVHk9zoRJHGcum6Sdl", "z4NfWkFvH5tpaLwNhRnzqFnOBHkdSCOJ8Wn084N9kV2vy");

	@Test
	public void testQuery() throws Exception {
		Twitter twitter = TwitterFactory.getSingleton();
		twitter.setOAuthConsumer("SRIvQAFNIXKaljSnTr5TiN9TT", "iVhR9o434UTJeWcuK6jmCKH7Kp5WvxoYV64mFJ2u9JVqhCL2La");
		twitter.setOAuthAccessToken(ACCESS_TOKEN);
		Query query = new Query("#CZ");
		query.setCount(100000);
		query.setLang("nl");
		query.setSince("2015-06-01");
		QueryResult result = twitter.search(query);
		for (Status status : result.getTweets()) {
			System.out.println("@" + status.getUser().getScreenName() + ": " + status.getText() + ": "
					+ status.getCreatedAt());
		}
	}

	@Test
	public void testStreaming() {
		TwitterStream twitterStream = new TwitterStreamFactory().getInstance();
		twitterStream.setOAuthConsumer("SRIvQAFNIXKaljSnTr5TiN9TT",
				"iVhR9o434UTJeWcuK6jmCKH7Kp5WvxoYV64mFJ2u9JVqhCL2La");
		twitterStream.setOAuthAccessToken(ACCESS_TOKEN);
		StatusListener listener = new StatusListener() {

			public void onStatus(Status status) {
				System.out.println("@" + status.getUser().getScreenName() + " - " + status.getText());
			}

			public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
				System.out.println("Got a status deletion notice id:" + statusDeletionNotice.getStatusId());
			}

			public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
				System.out.println("Got track limitation notice:" + numberOfLimitedStatuses);
			}

			public void onScrubGeo(long userId, long upToStatusId) {
				System.out.println("Got scrub_geo event userId:" + userId + " upToStatusId:" + upToStatusId);
			}

			public void onStallWarning(StallWarning warning) {
				System.out.println("Got stall warning:" + warning);
			}

			public void onException(Exception ex) {
				ex.printStackTrace();
			}

		};
		twitterStream.addListener(listener);
		twitterStream.sample();
	}
}
