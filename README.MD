# Junior automata tesztelői projektfeladat

## 1. Üzembehelyezési útmutató a tesztalkalmazáshoz (PrestaShop - Docker)

A környezet futtatásához telepített Docker és Docker Compose (pl. [Docker Desktop](https://docs.docker.com/desktop/setup/install/windows-install/)) szükséges.

1. Nyisson meg egy parancssort a projekt docker könyvtárában, ahol a "docker-compose.yml" fájl található: 

2. Adja ki a következő parancsot az alkalmazás elindításához, fontos hogy a Docker Desktop fusson a háttérben: 
    docker-compose up -d

3. Várjon 3-4 percet, amíg az adatbázis és a weboldal inicializálódik. (Mivel a `PS_INSTALL_AUTO=1` környezeti változó be van állítva, a PrestaShop az első induláskor a háttérben automatikusan feltelepíti a teljes webshopot és feltölti a demo termékekkel. Ez a folyamat a gép sebességétől függően 2-5 percet is igénybe vehet!)

4. Nyissa meg a böngészőben a tesztalkalmazást a következő címen: 
    http://localhost

5. A tesztelés befejezésével a környezet az alábbi paranccsal állítható le (a docker mappában kiadva):
    docker-compose down

## 2. Tesztek futtatásának manuális és automatizált módja

A projektet úgy készítettem el, hogy manuális és CI/CD pipeline-ból induló automatizált tesztfutást is egyaránt támogasson.

### Manuális mód:

* **Java fejlesztői környezetben (IDE)**
  1. A Maven projekt megnyitása a fejlesztői környezetben.
  2. Navigálás az src/test/java/tests mappába.
  3. Jobb klikk a kívánt teszten (osztályon) majd a szerkesztő tesztfuttató funkciójának (Junit Test) indítása.
* **Maven parancssorból**
  1. Parancssor megnyitása a projekt gyökérkönyvtárában a pom.xml fájl mellett.
  2. Csomag újrafordítása és az összes teszt futtatása:
     ```bash
     mvn clean test
     
* **Futtatás Github Action módban manuálisan**
  A GitHub felületén elérhető Actions fület megnyitva, és kiválasztva a workflow-t a bal oldali sávon, a Run workflow leosztásra kattintva manuálisan is kikényszeríthető egy teljes tesztfutás.

  GitHub elérés: 

### Automata mód:

A projekt GitHub Actions segítségével automatikusan futtatja a teszteket minden push vagy pull request esetén. Ez a folyamatos integráció (CI) biztosítja, hogy minden változtatás ellenőrizve legyen. Az eredmények a GitHub Actions fül alatt válnak elérhetővé amint a workflow lefut.

**Futtatás headless módban (CI kompatibilitás):**
A tesztrendszer (a BaseTest osztály alapján) fel van készítve a CI pipeline-ban történő futásra. Amennyiben a RUN_HEADLESS környezeti változó értéke true, a tesztek felület nélküli (headless) módban futnak le. Ilyenkor a rendszer paraméterezi a WebDriver-t a konténeres és virtuális környezetekhez (Linux) szükséges beállításokkal

## 3. Tesztjelentés (Allure Report) megtekintése

A tesztek lefutása után a részletes, vizuálisan formázott teszteredmények (Allure Report) az alábbi módokon tekinthetők meg:

### Parancssorból (Allure Serve):
Ez a legegyszerűbb módja a jelentés megtekintésének. Ez a parancs egy ideiglenes webszervert indít, ami feldolgozza és megjeleníti az eredményeket.

1. Győződjön meg róla, hogy a tesztek már lefutottak (mvn clean test paranccsal).
2. Adja ki a következő parancsot a projekt gyökérkönyvtárában a jelentés generálásához és azonnali megnyitásához a böngészőben:
   mvn allure:serve
3. A böngésző automatikusan megnyílik a tesztjelentéssel beépített grafikonokkal, lépésekkel és hibakeresési információkkal.
4. Ha végzett a jelentés böngészésével, a parancssorban a Ctrl + C billentyűkombinációval állíthatja le a szervert.

### Statikus HTML fájlok generálása:
Ha csak ki szeretné generálni a jelentést, de nem akar kiszolgálót indítani:
mvn allure:report

A kész HTML alapú jelentés a target/site/allure-maven-plugin/ mappába jön létre, ahonnan az index.html fájl böngészőben megnyitva később is megtekinthető (bár egyes böngészők biztonsági beállításai miatt helyiként megnyitva hiányos lehet).