# Project 4 - *Tweetz*

**Tweetzt** displays the Tweets from your Timeline. The app utilizes the Twitter REST API.


Submitted by: **Rakesh Nair Paruthikkat**


Time spent: **16** hours spent in total

## User Stories

The following **required** functionality is completed:

* [x] User can sign in to Twitter using OAuth login
* [x] User should be displayed the username, name, and body for each tweet
* [x] User should be displayed the relative timestamp for each tweet "8m", "7h"
* [x] User can view more tweets as they scroll with infinite pagination
* [x] User can click a "Compose" icon in the AppBar on the top right
* [x] User can then enter a new tweet and post this to twitter
* [x] User is taken back to home timeline with new tweet visible in timeline
* [x] Newly created tweet should be manually inserted into the timeline and not rely on a full refresh
* [x] User can view their home timeline tweets.
* [x] User can view the recent mentions of their username.
* [x] User can see picture, tagline, # of followers, # of following, and tweets on their profile.
* [x] User can see picture, tagline, # of followers, # of following, and tweets of clicked user.
* [x] Profile view displays the user's timeline
* [x] User can infinitely paginate any of these timelines (home, mentions, user) by scrolling to the bottom




The following **optional** features are implemented:

* [x] Robust error handling, check if internet is available, handle error cases, network failures.
* [x] While composing a tweet, user can see a character counter with characters remaining for tweet out of 140

The following **bonus** features are implemented:

* [x] Compose tweet functionality is build using Fragements
* [x] Replace all icon drawables and other static image assets with vector drawables where appropriate
* [x] On the Twitter timeline, leveraged the CoordinatorLayout.
* [x] On the profile screen, leverage the CoordinatorLayout to apply scrolling behavior as the user scrolls through the profile timeline



## Video Walkthrough

Here's a walkthrough of implemented user stories:

<img src='https://github.com/prnair09/Tweetz/blob/master/Demo.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />


## Open-source libraries used

- [Picasso](http://square.github.io/picasso/) - Image loading and caching library for Android
- [picasso transformations](https://github.com/wasabeef/picasso-transformations) - An Android transformation library providing a variety of image transformations for Picasso
- [Like Button](https://github.com/jd-alexander/LikeButton) - Like Button library.
- [Async Http client for Android](https://github.com/loopj/android-async-http) - An asynchronous, callback-based Http client for Android built on top of Apache's HttpClient libraries.
- [CodePath OAuth Handler](https://github.com/codepath/android-oauth-handler) - This library is an Android library for managing OAuth requests






## License

    Copyright [2017] [Rakesh Nair Paruthikkat]

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.