# GIPS

**GIPS** is an open-source framework for **G**raph-Based **I**LP **P**roblem **S**pecification.


## Installation (development)

* Install [AdoptOpenJDK 17 (HotSpot JVM)](https://adoptopenjdk.net/releases.html?variant=openjdk17&jvmVariant=hotspot) or newer.
* Install eMoflon::IBeX as described [here](https://github.com/eMoflon/emoflon-ibex#how-to-develop).
* Install at least one of the supported ILP solvers:
    * Install [Gurobi](https://www.gurobi.com/) in version `9.5.2` and activate a license for your computer.
        * Currently, Gurobi is the default ILP solver used in GIPS because of the great performance.
    * Install [GLPK](https://www.gnu.org/software/glpk/) in the newest version (`4.6.5`) and add it to your path.
        * GLPK is an open-source ILP solver that can be used without a charge.
    * Install [CPLEX](https://www.ibm.com/analytics/cplex-optimizer) in version `22.1.0` and activate a license for your computer (if neccessary).
        * CPLEX is a commercial alternative to the other ILP solvers implemented in GIPS.
* Clone this Git repository to your local machine and import it into Eclipse: *File -> Import -> General -> Existing Projects into Workspace*. Import all projects.
    * As an alternative, you can use this [PSF file](https://raw.githubusercontent.com/Echtzeitsysteme/gips/master/devProjectSet.psf).
* Inside the Eclipse development workspace ...
    * ... Run `GenerateGipsl.mwe2` from `org.emoflon.gips.gipsl/src/org.emoflon.gips.gipsl` with right click _Run As_ -> _MWE2 workflow_.
    * ... build all projects (*Project -> Clean... -> Clean all projects*) to trigger code generation.
* Launch a runtime workspace (while using a runtime Eclipse) as stated in the eMoflon::IBeX installation steps to start using GIPS.

A good start point to verify your installation is to run some of the [GIPS examples](https://github.com/Echtzeitsysteme/gips-examples) or the [GIPS tests](https://github.com/Echtzeitsysteme/gips-tests).

### Code-Style

This project uses the built-in code-style and code-formatter of Eclipse.
Before contributing, please set-up your Eclipse code-style settings as follows:

* _Window_ -> _Preferences_ -> _Java_ 
    * -> _Code Style_ -> _Clean Up_ -> _Active profile:_ -> "Eclipse [built-in]" (default)
    * -> _Code Style_ -> _Formatter_ -> _Active profile:_ -> "Eclipse [built-in]" (default)
    * -> _Code Style_ -> _Organize Imports: -> "java, javax, org, com" (default)
    * -> _Editor_ -> _Save Actions:
        * Check "Perform the selected actions on save"
        * Check "Format source code"
        * Check "Format all lines"
        * Check "Organize imports"
        * Check "Additional actions"

By using this settings, you should be unable to commit unformatted code.


## Installation (user)

* Install [AdoptOpenJDK 17 (HotSpot JVM)](https://adoptopenjdk.net/releases.html?variant=openjdk17&jvmVariant=hotspot) or newer.
* Install eMoflon::IBeX as described [here](https://github.com/eMoflon/emoflon-ibex#how-to-develop).
* Install at least one of the supported ILP solvers:
    * Install [Gurobi](https://www.gurobi.com/) in version `9.5.1` and activate a license for your computer.
        * Currently, Gurobi is the default ILP solver used in GIPS because of the great performance.
    * Install [GLPK](https://www.gnu.org/software/glpk/) in the newest version (`4.6.5`) and add it to your path.
        * GLPK is an open-source ILP solver that can be used without a charge.
* Install GIPS from the public Eclipse update site: https://echtzeitsysteme.github.io/gips-updatesite/snapshot/updatesite/
* Launch a runtime workspace (while using a runtime Eclipse) as stated in the eMoflon::IBeX installation steps to start using GIPS.


## Usage (running simulations)

Please refer to the [GIPS examples reposiory](https://github.com/Echtzeitsysteme/gips-examples).

## Tests

Please refer to the [GIPS tests repository](https://github.com/Echtzeitsysteme/gips-tests).


## License

This project is licensed under the GNU General Public License v3.0 - see the [LICENSE.md](LICENSE.md) file for more details.
