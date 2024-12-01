# Doorkey
### The Minimal and Intuitive Password Manager
[![License: GPL v3](https://img.shields.io/badge/License-GPLv3-blue.svg)](https://www.gnu.org/licenses/gpl-3.0) ![JUnit Tests](https://github.com/hydrabeer/doorkey/actions/workflows/ci-test.yml/badge.svg)
![CodeQL](https://github.com/hydrabeer/doorkey/actions/workflows/codeql-analysis.yml/badge.svg)
![Checkstyle](https://github.com/hydrabeer/doorkey/actions/workflows/checkstyle.yml/badge.svg)

<img alt="Logo" src="readme_img/doorkey.png" width="300">

## Table of Contents
1. [Contributors](#contributors)
2. [Overview](#overview)
3. [Features](#features)
4. [Installation](#installation)
5. [Usage](#usage)
6. [License](#license)
7. [Feedback](#feedback)
8. [Contributing](#contributing)

## Contributors
| Name            | Username                                       |
|-----------------|------------------------------------------------|
| Zachary Muir    | [@hydrabeer](https://github.com/hydrabeer)     |
| Saeed Al Shrouf | [@saeedshrouf](https://github.com/saeedshrouf) |
| Amaan Baweja    | [@9naama](https://github.com/9naama)           |
| Evan Su         | [@HACKERALERT](https://github.com/HACKERALERT) |
| Baris Bayazit   | [@bbayazit16](https://github.com/bbayazit16)   |

## Overview
### What it does
Doorkey keeps your passwords secure and remembers them for you. Unlike other password managers,
Doorkey lets you choose between storing your vault locally and in the cloud. Combined with being 
fully open source, this means Doorkey is configurable to be a true zero-trust solution or an
industry-standard cloud vault. Additionally, you can generate securely random passwords and copy them
to your clipboard for easy pasting.
### Why we made it
Doorkey was created out of a shared passion for cybersecurity and free software. Data breaches are 
becoming more common and more severe, and the best way to protect yourself is to use unique, complex 
passwords for each of your accounts. Doorkey makes it easy to generate and store these passwords, so 
you can stay safe online.

## Features
### Local and Cloud Storage
Create an encrypted local vault to store your credentials securely on your device, or use a cloud vault to access your
credentials from anywhere.
### Password Generation
Generate secure, random passwords with Doorkey's password generator. Customize the length,
character set, and other parameters to satisfy the requirements of any website. Passwords are generated using atmospheric noise,
which is a true source of random and thus highly secure.
### Password Strength Analysis
Get instant feedback on the strength of your passwords with smart password strength analysis.
### Search
Find your credentials quickly with Doorkey's search feature.
### One-Click Credential Copying
Copy your username and password to your clipboard with a single click to quickly log in to your 
accounts without having to remember or type out your credentials. Your clipboard is 
automatically cleared after a short period of time, so your credentials are never left exposed.
### One Click to Open Website
Save time by opening the website associated with your credentials with a single click.
### Secure Notes
Store sensitive information like security questions, answers, and other notes in a secure note.

## Installation
Doorkey requires Java to run and has been tested on Java 17 and later. Using earlier versions
of Java may or may not work. Doorkey runs on Windows, macOS, and most GNU/Linux distributions. To 
install Doorkey, follow these steps:
- Go to [Adoptium](https://adoptium.net/) and click the "Latest LTS Release" button to download 
Java 21. You can skip this step if you know you have Java 17 or later installed.

![A dark blue button with a download icon displaying the text "Latest LTS Release".](readme_img/latest_lts_release.png "Latest LTS Release Button")
- Install Doorkey by downloading the latest release from the 
[releases page](https://github.com/hydrabeer/doorkey/releases). 
- Double-click the downloaded JAR file to run Doorkey.

On Linux, you may need to make the file executable by running:
```bash
chmod +x doorkey.jar
```

## Usage
### First-Time Setup
When you first run Doorkey by double-clicking the `doorkey.jar` file, you can create cloud vault by 
clicking Sign Up. If you prefer to use a local vault, click Use Locally. You can always create a 
cloud vault later by signing up for an account. Make sure to choose a strong, memorable password
for your vault. A great way to do this is to use a 
[passphrase](https://www.privacyguides.org/en/basics/passwords-overview/#diceware-passphrases).

<img src="readme_img/welcome_view.png" width="300">

### Adding Credentials
To add a new credential, click the "+" button. Fill in the title, username, password, and URL 
fields, and click Save. You can also generate a password by clicking the icon next to the 
password field. Stay safe online by generating a unique password for each of your accounts.
### Using Credentials
Once you've added a credential, you can open it by clicking the unlock icon next to the credential.
From there, you can copy the username or password by clicking the clipboard icon next to the
field you want to copy. You can also open the website associated with the credential by clicking
the link icon next to the URL field.
### Editing Credentials
To edit a credential, open it and click the pencil icon at the top. Make your changes, and click 
the save icon.
### Deleting Credentials
To delete a credential, open it and click the trash icon at the top.
### Searching
To search for a credential, type your search query in the search bar at the top of the main vault
screen. You can search by title or by username.
## License
Doorkey is covered by the [GPLv3](https://www.gnu.org/licenses/gpl-3.0) (GNU General Public License version 3).
Check out the [quick guide](https://www.gnu.org/licenses/quick-guide-gplv3) to the license.

## Feedback
To give feedback on Doorkey, please fill out this [Google form](https://forms.gle/k4cEnGLPrFiXaTNx7).
### What counts as valid feedback?
Valid feedback is any information that helps us improve Doorkey. This can include bug reports,
feature requests, and general comments. Detailed and specific feedback is the most helpful. Try to
include as much information as possible, such as the steps to reproduce a bug or the reasoning 
behind a feature request.
### What to expect when submitting feedback
As busy students, we may not be able to respond to every piece of feedback. However, we value all
feedback and will do our best to consider it when planning future updates to Doorkey.

## Contributing
We welcome contributions and improvements to Doorkey through pull requests. We are happy to accept
a variety of improvements, such as bug fixes, new feature implementations, more tests, etc. To do this, you can fork
the repository with "Fork" in the top right of the page to create your own copy to work on. When you are finished
implementing your changes, you can create a pull request back to this repository and we will review it.

Pull requests should have a description detailing what changes were made, any introduced regressions or potential incompatibilities,
and a screenshot and/or instruction on how to use the new changes, if applicable. Make sure all the checks (JUnit tests, etc.) are passing
before requesting a review as this will ensure the code is ready to be reviewed with no immediate changes.
