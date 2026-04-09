# UW Seattle CSE 12X CI Template

## Usage

Make sure you have "make" installed to your PATH.

Use

```bash
make run module=<module name>
```

To run a project.

Example

```bash
make run module=c99-example-project
```

### Usage of .javawhitelistignore

`javaWhitelist` is a program which disallows forbidden methods from code.

The `.javawhitelistignore` file contains the paths from CWD (current working directory)
of the files that `javaWhitelist` should not parse and throw errors for. This is helpful
for provided Java files which do not follow the code quality policy of the class.

Note: current `.javawhitelistignore` may not be complete for all classes, but is complete for CSE122.

### Usage of allowlist.txt

`allowlist.txt` is a file for `javaWhitelist` which tells `javaWhitelist` which methods 
are allowed, i.e. not forbidden. Any method added there becomes an allowed one.

Note: current `allowlist.txt` setup may be wrong or not complete for some classes, but is complete for CSE122.

### Usage of checkstyle

The `checkstyle.xml` and `checkstyle-suppressions.xml` file are setup for all CSE 12X classes.

Certain methods included in files which are not ignored in `checkstyle-supressions.xml`, since they 
contain both user code and provided code, may need to be manually flagged as ignored for checkstyle 
to correctly check only user code.

Use these suppression warnings on methods of this type:

- For Java methods with a missing JavaDoc:
```java
@SuppressWarnings("checkstyle:MissingJavadocMethod")
```

- For Java methods with a JavaDoc that is incomplete
```java
@SuppressWarnings("checkstyle:JavadocMethod")
```

## Setup Pre-commit

Setup pre-commit hook

```bash
ln -s ../../scripts/pre-commit.sh .git/hooks/pre-commit
chmod +x scripts/pre-commit.sh
```

## Adding a new project

Make a new directory per project with internal structure src/main/java.
Place a pom.xml at root of the new directory and copy and paste the pom.xml
from c99-example-project and change `artifactId` and `mainClass` only.

Note: `mainClass` points to the file which contains the java main class.


