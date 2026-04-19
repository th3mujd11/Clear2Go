

Clear2Go

Clear2Go este o soluție inovatoare pentru rezolvarea problemelor persistente în comunicațiile radio.Aplicația urmărește completarea acestora și îmbunătățirea cooperării dintre piloți și controlorii de trafic aerian. Ea gestionează autorizațiile emise de turnurile de control ale aerodromurilor civile pentru avioane.

I. Aplicația pentru Mobil

Proiectarea arhitecturală:
Aplicația a fost dezvoltată utilizând Java, asigurând compatibilitatea cu telefoanele care rulează cel puțin Android 10, fapt ce ne permite să acoperim aproximativ 75% din dispozitivele mobile actuale. Alegerea Java ca limbaj de programare ne oferă stabilitate și o integrare facilă cu ecosistemul Android.
Utilizarea aplicației
Aplicația oferă una din două funcții în funcție de ce rol îi este atribuit contului utilizatorului. Aceste două funcții sunt:
1. Pilot:
Utilizatorul va avea opțiunea de a cere autorizații în următoarea ordine, apăsând pe butoanele:
- Pornire motor (pentru cererea autorizării de pornire a aeronavei)
- Rulaj (pentru cererea autorizării de a pleca de la poziția de parcare spre aliniere)
- Intrare Aliniere (pentru cererea autorizării de a intra în aliniere)
- Plecare (pentru cererea autorizării de decolare)
- Aterizare (pentru cererea autorizării de aterizare)
- Exit (pentru a ieși din această funcție a aplicației)

Aplicația afișează pe ecran și altitudinea și viteza. Inițial, toate butoanele sunt roșii. Dacă unul dintre ele este apăsat, acesta devine portocaliu, confirmând că cererea a fost trimisă, și ulterior verde dacă este acceptată sau roșu dacă este respinsă.

2. Controlor:
În partea superioară a ecranului vor apărea cererile de autorizare ale piloților. Cererile sunt de următoarea formă:
1. Numele aeronavei
2. Tipul autorizației cerute (pornire motor, rulaj, intrare aliniere, plecare, aterizare)
3. Opțiunea de a o aproba (apasând butonul allow) sau de a o refuza (apasând butonul deny)
În partea inferioară a ecranului poate fi găsită harta pe care figurează poziția live a tuturor avioanelor, transmisă de GPS-ul telefoanelor mobile ale piloților aflați la bord. Apăsarea butonului map extinde harta pe tot ecranul telefonului și face posibilă navigarea acesteia prin swipe-uri și posibilitatea de zoom-in și zoom-out.

II. Site-ul Web

Proiectare: Site-ul a fost realizat prin îmbinarea limbajelor HTML și JavaScript, oferindu-i o structură stabilă și interactivă.
Funcționalități: Site-ul include o hartă pe care sunt afișate pozițiile avioanelor în timp real, preluate din GPS-ul telefoanelor mobile ale piloților care utilizează aplicația de mobil. Dând click dreapta pe un avion, deasupra acestuia va apărea numele pilotului.

Infrastructura backend:
În spatele aplicației funcționează un set de SDK-uri (Software Development Kit) furnizate de Firebase, un serviciu dezvoltat de Google, care ne permit să implementăm autentificarea OAuth cu contul personal Google și să avem o bază de date dinamică. Firebase oferă o suită completă de instrumente care facilitează dezvoltarea, testarea și monitorizarea aplicației, contribuind astfel la o dezvoltare mai rapidă și mai eficientă.





Securitate și autentificare
În dezvoltarea unui software destinat gestionării comunicațiilor dintr-un aerodrom, securitatea reprezintă un aspect esențial. Pentru a asigura un nivel ridicat de protecție împotriva unor posibile amenințări, am optat pentru utilizarea standardului OAuth. Firebase este o platformă de dezvoltare de aplicații mobile și web oferită de Google, care include o gamă largă de servicii, inclusiv bază de date în timp real, autentificare, stocare în cloud și multe altele. Utilizând Firebase pentru securitatea comunicațiilor, beneficiem de infrastructura solidă și actualizată oferită de Google, reducând riscurile asociate cu posibilele vulnerabilități ale unor sisteme mai vechi sau mai puțin fiabile. Pentru a completa stratul de securitate al comunicațiilor între piloți și controlori, ne-am bazat pe bazele de date Firebase, care oferă facilități puternice de criptare a datelor. Folosind algoritmul SHA-256, toate transmisiunile sunt criptate înainte de a fi trimise către bazele de date Firebase, asigurând astfel confidențialitatea și integritatea informațiilor în tranzit.
Prima bază de date este destinată stocării utilizatorilor, identificându-i prin intermediul unui token generat aleator după autentificarea prin metoda OAuth. Utilizatorii sunt clasificați în două categorii distincte: piloți și controlori. Baza de date include numele și o fotografie a utilizatorului, utilizate ulterior pentru autentificarea și afișarea acestora în aplicație. În ceea ce privește poziționarea avioanelor, al doilea set de date stochează coordonatele geografice (latitudine și longitudine) ale aeronavelor în timp real. Aceste coordonate sunt obținute direct din GPS-ul dispozitivelor mobile ale piloților, actualizându-se continuu pentru a reflecta poziția actuală a aeronavelor. O astfel de abordare permite vizualizarea precisă și în timp real a mișcărilor aeronavelor pe harta integrată în aplicație.

Structura și Gestionarea Datelor
A doua bază de date este organizată ierarhic și conține informații despre utilizarea aplicației. Aceasta permite adăugarea de implementări suplimentare ale aplicației și gestionarea locațiilor asociate. Pentru transmiterea informațiilor în această bază de date, utilizăm un model key-value (Boolean), similar cu structura de date Map din STL. În situațiile în care avem nevoie de gestionarea unor date mai complexe, folosim o clasă care în prezent conține maxim două componente: requester și requests.
Fluxul de Autorizare și Funcționalități
Pentru a asigura că utilizatorii urmează pașii corecți în procesul de autorizare, am implementat metodele bazei de date Firebase într-o manieră secvențială, astfel încât fiecare pas să fie deblocat succesiv. Acest lucru previne omisiunile neintenționate și asigură un flux coerent al aplicației.

Funcționalități GPS
În prezent, aplicația utilizează exclusiv tehnologia GPS a telefonului pentru a determina altitudinea față de izobara de 1013,2 hPa, viteza de deplasare și poziția (longitudine și latitudine), stocând aceste date online. Această funcționalitate este esențială pentru o gamă variată de aplicații, de la monitorizarea activităților fizice la navigație și multe altele. Tehnologia GPS integrată ne permite să oferim utilizatorilor date precise și în timp real, esențiale pentru funcționalitatea aplicației.

Interfața Utilizatorului
În urma sondării opiniilor piloților, am decis să folosim un UI (User Interface) cât mai simplu pentru aplicația noastră. Această decizie se bazează pe nevoia de a comunica doar strictul necesar, asigurând astfel o interacțiune scurtă și la obiect. Prin simplificarea interfeței, dorim să:

-Îmbunătățim eficiența operațională: Piloții vor putea accesa rapid informațiile esențiale, fără a fi distrași de elemente inutile sau complexe.
-Reducem timpul de răspuns: O interfață minimalistă permite o navigare mai rapidă, reducând timpul necesar pentru completarea sarcinilor.
-Minimalizăm riscul de eroare: O interfață clară și simplă reduce confuzia și erorile, ceea ce este crucial într-un domeniu unde precizia este esențială.
-Creăm o experiență de utilizare intuitivă: Prin eliminarea elementelor redundante, piloții pot interacționa cu aplicația fără a necesita instruiri suplimentare extinse.

În concluzie, simplificarea UI-ului reflectă un răspuns direct la feedback-ul primit de la piloți și subliniază angajamentul nostru de a crea o aplicație eficientă, intuitivă și adaptată nevoilor reale ale utilizatorilor noștri.
Scalabilitate și Extensibilitate
Una dintre prioritățile noastre este asigurarea scalabilității și extensibilității aplicației. Utilizând Firebase, avem posibilitatea de a scala baza de date și infrastructura backend pe măsură ce numărul de utilizatori crește, fără a compromite performanța. În plus, structura modulară a codului ne permite să adăugăm rapid noi funcționalități și să integrăm alte servicii, cum ar fi notificările push, analizele de utilizare și suportul pentru mai multe limbaje.
Monitorizare și Analiză
Pentru a îmbunătăți continuu aplicația, utilizăm Firebase Analytics, care ne oferă date valoroase despre comportamentul utilizatorilor. Aceste informații ne permit să identificăm rapid problemele, să optimizăm experiența utilizatorilor și să adaptăm aplicația în funcție de feedback-ul primit.

Suport și Actualizări
Planificăm să oferim suport continuu pentru utilizatori, prin actualizări regulate și rezolvarea promptă a problemelor raportate. În plus, vom adăuga noi funcționalități în funcție de nevoile și cerințele utilizatorilor, menținând aplicația relevantă și utilă în timp.

Stabilitatea Aplicației
Stabilitatea și eficiența aplicației noastre au fost priorități de la începutul proiectului. Am realizat că software-ul trebuie să funcționeze fără probleme pentru a oferi o experiență plăcută utilizatorilor noștri și pentru a ne atinge cu succes obiectivele.

Pentru a asigura această stabilitate, am adoptat o abordare simplă și concentrată asupra esențialului. Am restrâns funcționalitățile aplicației la ceea ce este absolut necesar, eliminând orice element care ar putea introduce complexitate și potențiale puncte de eșec. Acest lucru nu numai că a îmbunătățit fiabilitatea, dar a permis și un proces mai rapid de remediere a erorilor și de implementare a altor funcții în viitor.

Arhitectura simplă a aplicației a servit ca fundație solidă pentru stabilitate și performanță. Am evitat supraîncărcarea cu caracteristici suplimentare și ne-am concentrat pe asigurarea unei experiențe fără cusur pentru utilizatori. Astfel, am fost capabili să ne concentrăm resursele pe îmbunătățirea calității și pe răspunsul rapid la orice problemă care ar putea apărea.

De asemenea, am implementat teste riguroase și procese de verificare pentru a detecta și remedia orice erori sau vulnerabilități înainte ca acestea să afecteze utilizatorii noștri. Această abordare preventivă ne-a permis să menținem o rată scăzută a erorilor și să oferim o experiență de utilizare consistentă și fiabilă.

Versionare

Echipa noastră a decis să adopte GitHub ca sistem de versionare pentru proiectul nostru. Acest lucru ne permite să urmărim întregul parcurs al dezvoltării aplicației prin intermediul repository-ului dedicat. Astfel, fiecare modificare, actualizare sau adăugare la codul sursă poate fi monitorizată și gestionată în mod eficient. GitHub facilitează colaborarea între membrii echipei și contribuie la organizarea și documentarea proiectului într-un mod transparent și accesibil pentru toți cei implicați.


Instalarea aplicatiei Clear2Go:
	Aplicatia poate fi instalată foarte simplu prin descărcarea si rularea apk-ului de pe github aflat la adresa: https://github.com/TheMujdii3/Clear2Go/releases/tag/Nationala
 
Acest proiect a fost realizat de către Neculai-Mirea Andrei-Laurențiu și Dunel Ștefan-Octavian, elevi în clasa a X-a A la Colegiul Național Mihai Viteazul din Slobozia, județul Ialomița. Vă mulțumim pentru atenția și timpul acordat parcurgerii acestui document!

Resure:
Reposytory-ul de pe github: https://github.com/TheMujdii3/Clear2Go
Android Studio: https://developer.android.com/studio
Firebase: https://firebase.google.com/
Firebase Analytics: https://firebase.google.com/docs/analytics

IP-uri aplicatie web: to be completed

Tutorial utilizare aplicatie web : https://drive.google.com/file/d/1xvFSC--XIOhs8RGfQ4frBat3GXSfbPH8/view?usp=sharing

