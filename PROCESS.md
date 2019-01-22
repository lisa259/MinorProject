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


