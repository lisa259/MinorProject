# dag 1 (woensdag)  
Alle  hoofdschermen/activities gemaakt. (nog geen activities om gridviews te vullen)  
  
# dag 2 (donderdag)  
Alle knoppen/gridviews gekoppeld aan onClick  
  
# dag 3 (vrijdag)  
Poging tot het gebruiken van firebase  
  
# dag 4 (maandag)  
van firebase overstappen op rester.py, want dit werd aangeraden.  
Beslissing hoe ik rester ga indelen. Besluit: voor iedere table in de database een aparte link(/). Bijvoorbeeld serverlink.nl/login,  
serverlink.nl/kledingitems.  Kwam erachter dat android studio niet goed gepushed was naar github, dus pas vanaf vandaag goed geupload.
  
# dag 5 (dinsdag)  
Start helper class voor login/aanmeldenactivity. GetRequests voor https://ide50-lisabeek.legacy.cs50.io:8080/login.  
Bij LoginActivity vergeten implements LoginHelper.Callback te doen, die een error veroorzaakte waar ik lang mee geprutst heb..  
  
# dag 6 (woensdag)  
Fout van gister ontdekt.. Vandaag GetRequest en Postrequest voor LoginActivity en AanmeldenActivity geimplementeerd inclusief   
EditTexten gekoppeld.  
  
# dag 7 (donderdag)  
Bij de garderobe de schermen met categorieën en items samenvoegen. Want als de gridview leeg is (er zijn geen categorieen),  
dan kan er dus niet doorgeklikt worden naar de items. Terwijl je op dit moment pas in het item scherm een  
nieuw item kan toevoegen. Dus het plan is nu om in activity_garderobe een spinner te implementeren die alle categorieen bevat,   
met daaronder een gridview met alle items uit de geselecteerde categorie. Daarnaast komt er een + knop rechtsboven in het   
scherm om via daar nieuwe items(en eventueel een nieuwe categorie) toe te voegen ipv dat hieronderaan het scherm een knop voor is.  
  
# dag 8 (vrijdag)  
image uploaden van gallerij als Uri. dit omzetten naar bitmap, vervolgens met base64 naar string, zodat het opgeslagen kan worden op de server.  
  
# dag 9 (maandag)  
Bij decoden van de Base64-string. gebruikmaken van byte[] decoded = Base64.decode(fotoString, Base64.URL_SAFE) ipv byte[] decoded = Base64.decode(fotoString, Base64.DEFAULT), want dit schijnt problemen, die veroorzaakt kunnen worden omdat er met JSON wordt gewerkt, te voorkomen.  
https://stackoverflow.com/questions/4837110/how-to-convert-a-base64-string-into-a-bitmap-image-to-show-it-in-a-imageview  
  
# dag 10 (dinsdag)  
Verwijderen van een kledingitem door deze lang aan te klikken in de garderobe ipv door een verwijder-knop in activity_item.xml. Delete item request geimplementeerd.  
  
# dag 11 (woensdag)  
De knop in activiry_item_xml veranderen van verwijderen naar aanpassen. Put request geimplementeerd.  
Besloten om de vrienden functie weg te laten, dit zal teveel werk worden. Hierdoor vervallen extra functies als profielfoto (heeft niet veel zin zonder vrienden die het kunnen zien) en alle activities voor vrienden.   
  
# dag 12 (donderdag)  
Besloten om een database binnen de app te implementeren, deze bij het opstarten van de app te vullen met alle items/lookbooks. Dit zodat er maar 1 keer de get request hoeft te worden gedaan (inlogrequest wordt wel apart gedaan). Omdat er geen vrienden-optie meer bijkomt is dit prima mogelijk, want hetgeen dat andere posten/pullen/deleten heeft geen impact op jouw items etc.  
  
# dag 13 (vrijdag) 
Database legen bij het openen van het inlogscherm. Zodat wanneer er van gebruiker gewisseld wordt, de data van deze nieuwe inlogger up to date is. Garderobe optie werkt nu helemaal. GarderobeActivity kopieëren in WishlistActivity en wat kleine aanpassingen gedaan. Wishlist is nu ook klaar.  
  
# dag 14 (maandag)  
Implementatie lookbook. Besloten om van alle items van één look het id op te slaan in een string, dit met spaties ertussen. Dit gedaan zodat het op te slaan is op de server. In de app wordt dit omgezet naar een String[], zodat het gebruikt kant worden in de sql queries.     
# dag 15 (dinsdag)  
# dag 16 (woensdag)  


