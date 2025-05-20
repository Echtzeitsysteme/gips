# GIPS

**GIPS** is an open-source framework for **G**raph-Based (M)**I**LP **P**roblem **S**pecification.


## Installation (development)

* Install [AdoptOpenJDK 21 (HotSpot JVM)](https://adoptopenjdk.net/releases.html?variant=openjdk21&jvmVariant=hotspot) or newer.
* Install eMoflon::IBeX as described [here](https://github.com/eMoflon/emoflon-ibex#how-to-develop).
* Install at least one of the supported (M)ILP solvers:
    * Install [Gurobi](https://www.gurobi.com/) in version `12.0.2` and activate a license for your computer.
        * Currently, Gurobi is the default (M)ILP solver used in GIPS because of the great performance.
    * Install [GLPK](https://www.gnu.org/software/glpk/) in the newest version (`4.6.5`) and add it to your path.
        * GLPK is an open-source (M)ILP solver that can be used without a charge.
        * For Windows-based systems, follow these steps to install GLPK:
            * Download [winglpk](https://sourceforge.net/projects/winglpk/files/winglpk/GLPK-4.65/).
            * Extract the archive, e.g., to `C:\Program Files\GLPK\glpk-4.65`.
            * Add `C:\Program Files\GLPK\glpk-4.65\w64` to the system-wide environment variable `path`.
            * Restart your Eclipse IDE.
    * Install [CPLEX](https://www.ibm.com/analytics/cplex-optimizer) in version `22.1.2` and activate a license for your computer (if necessary).
        * CPLEX is a commercial alternative to the other (M)ILP solvers implemented in GIPS.
* Clone this Git repository to your local machine and import it into your Eclipse development workspace: *File -> Import -> General -> Existing Projects into Workspace*. Import all projects. You can use your eMoflon::IBeX development workspace for this, i.e., there is no need to start a runtime workspace for the GIPS development projects.
    * As an alternative, you can use this [PSF file](https://raw.githubusercontent.com/Echtzeitsysteme/gips/master/devProjectSet.psf) for the import.
* Inside the Eclipse development workspace ...
    * ... Run `Run all GIPS MWE2 files.launch` from `org.emoflon.gips.core/launch` with right click _Run As_ -> _Build all GIPS MWE2 files_.
        * In case any error warning pops up, simply click `Proceed`.
    * ... build all projects (*Project -> Clean... -> Clean all projects*) to trigger code generation.
* Launch a runtime workspace (while using a runtime Eclipse) as stated in the eMoflon::IBeX installation steps to start using GIPS.

A good start point to verify your installation is to run some of the [GIPS examples](https://github.com/Echtzeitsysteme/gips-examples) or the [GIPS tests](https://github.com/Echtzeitsysteme/gips-tests).

### Code-Style

This project uses the built-in code-style and code-formatter of Eclipse.
Before contributing, please set-up your Eclipse code-style settings as follows:

* _Window_ -> _Preferences_ -> _Java_
    * -> _Code Style_ -> _Clean Up_ -> _Active profile:_ -> "Eclipse [built-in]" (default)
    * -> _Code Style_ -> _Formatter_ -> _Active profile:_ -> "Eclipse [built-in]" (default)
    * -> _Code Style_ -> _Organize Imports:_ -> "java, javax, org, com" (default)
    * -> _Editor_ -> _Save Actions:_
        * Check "Perform the selected actions on save"
        * Check "Format source code"
        * Check "Format all lines"
        * Check "Organize imports"
        * Check "Additional actions"

By using this settings, you should be unable to commit unformatted code.


## Installation (user)

* Install [AdoptOpenJDK 21 (HotSpot JVM)](https://adoptopenjdk.net/releases.html?variant=openjdk21&jvmVariant=hotspot) or newer.
* Install eMoflon::IBeX as described [here](https://github.com/eMoflon/emoflon-ibex#how-to-develop).
* Install at least one of the supported (M)ILP solvers:
    * Install [Gurobi](https://www.gurobi.com/) in version `12.0.2` and activate a license for your computer.
        * Currently, Gurobi is the default (M)ILP solver used in GIPS because of the great performance.
    * Install [GLPK](https://www.gnu.org/software/glpk/) in the newest version (`4.6.5`) and add it to your path.
        * GLPK is an open-source (M)ILP solver that can be used without a charge.
    * Install [CPLEX](https://www.ibm.com/analytics/cplex-optimizer) in version `22.1.2` and activate a license for your computer (if necessary).
        * CPLEX is a commercial alternative to the other (M)ILP solvers implemented in GIPS.
* Install GIPS from the public Eclipse update site: https://echtzeitsysteme.github.io/gips-updatesite/snapshot/updatesite/
* Launch a runtime workspace (while using a runtime Eclipse) as stated in the eMoflon::IBeX installation steps to start using GIPS.


## Environment variables

When using GIPS within the Eclipse IDE, the following environment variables may be necessary for your runtime configuration:
```
# Linux
GRB_LICENSE_FILE=/home/mkratz/gurobi.lic
GUROBI_HOME=/opt/gurobi1202/linux64/
LD_LIBRARY_PATH=/opt/gurobi1202/linux64/lib/:/opt/ibm/ILOG/CPLEX_Studio2212/cplex/bin/x86-64_linux/
PATH=/opt/gurobi1202/linux64/bin/:/opt/ibm/ILOG/CPLEX_Studio2212/cplex/bin/x86-64_linux/:$PATH

# macOS
GRB_LICENSE_FILE=/Users/mkratz/gurobi.lic
GUROBI_HOME=/Library/gurobi1202/macos_universal2/
LD_LIBRARY_PATH=/Library/gurobi1202/macos_universal2/lib/:/Applications/CPLEX_Studio2212/cplex/bin/arm64_osx/
PATH=/Library/gurobi1202/macos_universal2/bin/:/Applications/CPLEX_Studio2212/cplex/bin/arm64_osx/:$PATH

# Windows
GRB_LICENSE_FILE=C:\Users\mkratz\gurobi.lic
GUROBI_HOME=C:\gurobi1202\win64
LD_LIBRARY_PATH=C:\gurobi1202\win64\lib;C:\Program Files\IBM\ILOG\CPLEX_Studio2212\cplex\bin\x64_win64\
PATH=C:\gurobi1202\win64\bin;C:\Program Files\IBM\ILOG\CPLEX_Studio2212\cplex\bin\x64_win64\
```


## Usage (running simulations)

Please refer to the [GIPS examples repository](https://github.com/Echtzeitsysteme/gips-examples).


## Tests

Please refer to the [GIPS tests repository](https://github.com/Echtzeitsysteme/gips-tests).


## License

This project is licensed under the GNU General Public License v3.0 - see the [LICENSE](LICENSE) file for more details.
