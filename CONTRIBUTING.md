# Contributing

We're glad you want to help us out and make this product the best that it can be! We have a few simple things to follow
when making changes to files and adding new features.

### Project Branches

This section mainly applies to those with read/write access to our repositories, but can be helpful for others.

The `develop` branch should always be in a runnable state, but can contain newest code changes. However, you should
create `feature/` branches in order to add new functionality or change how things work. When making a feature branch, if
it is referencing something in the issue tracker, please title the branch `feature/NAME-###` where `###` is the issue
number.

Moving forward all commits from contributors should be in the form of a PR, unless it is something we have previously
discussed as being able to be pushed right into `develop`.

All new code should contain unit tests at a minimum (where applicable). There is a lot of uncovered code currently, so
as you are doing things please be looking for places that you can write tests.

The `master` branch contains the current production environment.

### Update the CHANGELOG

When adding something that is new, fixed, changed, or security-related for the next release you should be adding a note
to the CHANGELOG. If something is changing within the same version (i.e. fixing a bug introduced but not released) it
should _not_ go into the CHANGELOG.

### Updating of non-source-code files

If you need to update any files outside of the src/ folder, please follow those steps:

1. All changes which are applicable to other repositories of the same project should be made there too.
2. All non-source-code changes must be written to the changelog immediately.

(This is to ensure that all non-source-code files are being kept up-to-date across repositories in the same project and
to find pipeline errors / dependency errors faster.)

### Code Guidelines

Please use IntelliJ with the Lombok plugin, the save action plugin, the rainbow brackets plugin and the SonarLint (
standard settings) plugin. Please activate optimize imports and reformat file for the save action plugin. (Settings ->
Other Settings -> Save action plugin)

Please keep in mind the general clean code guidelines such as `no code duplication` and others.

Here are some additional rules that every developer should follow:

Rule 0: Please use a linter, such as SonarLint for IntelliJ  
Rule 1: Follow a consistent coding standard (code conventions)  
Rule 2: Name things properly, long variable and function names are allowed  
Rule 3: Be expressive, write code as you speak and be optimally verbose  
Rule 4: Max indent per method should be 2, in case of exceptions 3  
Rule 5: Avoid creating god object and long methods  
Rule 6: Keep the method in one place, inject the class and call it, DRY  
Rule 7: Avoid in-line comments (comment with code), put comments in the method doc

### Responsible Disclosure

This is a fairly in-depth project and makes use of a lot of parts. We strive to keep everything as secure as possible
and welcome you to take a look at the code provided in this project yourself. We do ask that you be considerate of
others who are using the software and not publicly disclose security issues without contacting us first by email.

We'll make a deal with you: if you contact us by email and we fail to respond to you within a week you are welcome to
publicly disclose whatever issue you have found. We understand how frustrating it is when you find something big and no
one will respond to you. This holds us to a standard of providing prompt attention to any issues that arise and keeping
this community safe.

If you've found what you believe is a security issue please email us at `business@jandev.de`.

### Where to find Us

We're active right here on Gitlab. If you encounter a bug or other problems, open an issue on here for us to take a look
at it. We also accept feature requests here as well.