# TroyTD
 A tower defence game. Based on the trojan war. Build with [libGDX](https://github.com/libgdx/libgdx). 
## TOC
1. [Usage](#usage)
2. [Build Guide](#build-guide)
3. [Controls](#controls)
4. [Screenshots](#screenshots)
5. [Sources](#sources)
## Usage
[Download the latest release.](https://github.com/quiode/TroyTD/releases)  
For android, move the apk file to your phone and open in.  
On Desktop, open the java file with java (preferred version 11) or run `java -jar [filename]`.  
For browsers, download the zip file, unzip it and open the html file with your preferred browser.
## Build Guide
Compiling from source is really easy as explained below.
### Download the Repository
#### Git
```bash
git clone https://github.com/quiode/TroyTD
```
#### GitHub CLI
```bash
gh repo clone https://github.com/quiode/TroyTD
```
#### GUI
1. Click on **Code**  
![image](https://user-images.githubusercontent.com/51075975/143856848-f05fa387-ccc1-4671-86c0-8b7cf52b4c79.png)
1. **Download ZIP**  
![image](https://user-images.githubusercontent.com/51075975/143857037-5f751b5c-9fe8-4155-9d96-373a8436b08b.png)
1. Unzip File
### Build it
#### Desktop
1. run `./gradlew desktop:dist`
2. the file is located under `desktop/build/libs/`
3. run it with [java](https://www.oracle.com/java/technologies/java-se-development-kit11-downloads.html) (`java -jar [filename]`)
#### Android
1. run `./gradlew android:assembleRelease`
2. the file is located under `android/build/outputs/apk`
3. move it on your phone and open it to install
#### Web
1. run `./gradlew html:dist`
2. the files are located under `html/build/dist/`
3. open the html file with a webbrowser or upload it to a webserver
# Controls
## In-Game
- **<ESC\>**: Pause
# Screenshots
##Main Menu
![image](./screenshots/Screenshot%20from%202022-01-04%2013-56-29.png)
##Settings
![image](./screenshots/Screenshot%20from%202022-01-04%2013-56-34.png)
##Game
![image](./screenshots/Screenshot%20from%202022-01-04%2013-56-43.png)
##Map Select
![image](./screenshots/Screenshot%20from%202022-01-06%2010-24-49.png)
# Sources
- Sound Effects: [Chosic](https://www.chosic.com/)
