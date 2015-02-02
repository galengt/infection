# Spreading new versions of the site - aka Infection

## Running tests with Juit

The unit tests have fairly verbose output, so you can get a pretty good sense of what is going on just from the command line.

    cd Infection
    java -cp "test/lib/junit-4.10.jar:bin" org.junit.runner.JUnitCore infect.InfectorImplTests

## Visualization with Processing

Navigate to Infection/procession/infection in Finder or the Windows equivalent. 

For mac osx continue to application.macosx and there you will find infection.app.
For windows run the .exe in the folder windows32 or windows64, depending on your operating system. 