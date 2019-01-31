# Dress My Closet  
  
De garderobe app voor een overzicht en frisse blik op je garderobe.  
  
Lisa Beek 12441392
Programmeerproject  
Minor Programmeren  
Universiteit van Amsterdam  

<img src="https://github.com/lisa259/MinorProject/blob/master/doc/garderobe.png" width="150">

# Design 
### Overzicht  
![img](https://github.com/lisa259/MinorProject/blob/master/doc/eind2.png)  
  
### Detail  
#### Login  
![img](https://github.com/lisa259/MinorProject/blob/master/doc/logind.png)  
  
#### Garderobe  
![img](https://github.com/lisa259/MinorProject/blob/master/doc/garderobed.png) 
  
#### Wishlist  
![img](https://github.com/lisa259/MinorProject/blob/master/doc/wishlistd.png) 
  
#### Lookbook  
![img](https://github.com/lisa259/MinorProject/blob/master/doc/lookbookd.png) 
  
# Bestanden  
### Activities  
#### LoginAcitivity  
- Item/lookbook data van de server halen en inserten in database.  
- Navigeer naar GarderobeActivity: inloggeggevens checken met data van server.  
- Navigeer naar RegistreerActivity
  
#### RegistreerAcitivity  
- Navigeer naar GarderobeActivity: checken of ingevoerde gegevens nog niet gebruikt zijn door een andere gebruiker met data van server. En post nieuwe inloggegevens op server.  
  
#### GarderobeAcitivity  
- Vul spinner met alle categorieën van de gebruiker in garderobe. 
- Wanneer een categorie selecteerd is, vul de gridview met alle items uit deze categorie. 
- Delete item door er lang op te klikken.  
- Navigeer naar ItemActivity.  
- Navigeer naar NieuwItemActivity.  
- Navigeer naar WishlistActivity.  
- Navigeer naar LookbookActivity.  
  
#### WishlistAcitivity  
- Vul spinner met alle categorieën van de gebruiker in wishlist. 
- Wanneer een categorie selecteerd is, vul de gridview met alle items uit deze categorie. 
- Delete item door er lang op te klikken.  
- Navigeer naar ItemActivity.  
- Navigeer naar NieuwItemActivity.  
- Navigeer naar GarderobeActivity.  
- Navigeer naar LookbookActivity.  
  
#### ItemAcitivity  
- Toon details over het geselecteerde item.  
- Nagiveer naar AanpassenItemActivity. (Mist ItemActivity is bereikt via garderobe/wishlist)
  
#### AanpassenItemAcitivity  
- Geeft de optie om een andere foto uit de galerij te kiezen.  
- Geeft de optie om de categorie te veranderen.  
- Geeft de optie om het merk te veranderen.  
  
#### NieuwItemAcitivity  
- Geeft de optie om een foto uit de galerij te kiezen.  
- Geeft de optie om een categorie toe te kennen.  
- Geeft de optie om een merk toe te kennen. 
  
#### LookbookAcitivity  
- Geeft per rij een aantal items uit een lookbook weer.  
- Navigeer naar OutfitActivity door op het plusje te klikken, om een nieuwe outfit te maken.  
- Navigeer naar OutfitActivity door op een lookbook te klikken, om deze uit te breiden of te bekijken.  
- Verwijder een lookbook door deze lang vast te houden.  
  
#### OutfitAcitivity  
- Geeft alle items in een lookbook weer.  
- Navigeer naar OutfitItemActivity door op het plusje te klikken.  
- Navigeer naar ItemActivity door op een item te klikken.  
  
#### OutfitItemAcitivity  
- Vul spinner met alle categorieën van de gebruiker. 
- Wanneer een categorie selecteerd is, vul de gridview met alle items uit deze categorie.  
- Navigeer terug naar OutfitActivity door op een item te klikken en deze toe te voegen aan de look.  
   
### Helpers  
#### LoginHelper 
- Get request van de /login server.  
- Post request op de /login server.  
  
#### ItemHelper 
- Get request van de /items server.  
- Post request op de /items server.  
- Delete request op de /items server.  
- Put request op de /items server.  
  
#### LookbookHelper 
- Get request van de /lookbook server.  
- Post request op de /lookbook server.  
- Delete request op de /lookbook server.  
- Put request op de /lookbook server. 
  
### Adapters  
#### GridItemAdapter  
- Kent voor alle items in cursor, een foto toe aan de grid.  
  
#### LookbookAdapter 
- Kent voor alle lookbooks in cursor, maximaal 4 fotos toe aan de grid.  
    
### Database 
#### Database 
- Create table items en table lookbook.  
- Insert items en lookbooks.  
- Delete rijen van table items en table lookbook.  
- Leeg de database iedere keer als LoginActivity wordt geopend.  
- Select verschillende opties.  
  
# Challenges  
- Teveel willen implementeren, al in de basis, waardoor het niet gelukt is extra opties te maken.  
- Android Studio koppelen aan repo waar al bestanden in staan..  
- Put request naar de server kostte veel tijd om goed te krijgen, doordat er een fout in het aanroepen zat (gaf null waardes mee).  
- Legen van de Database en deze returnen als lege database, kostte lang om werkend te krijgen.  
- Het variabel maken van een select querie in de database kostte veel tijd, onderzoek en proberen.  
- Bij het selecten van alle items met bepaalde id's, duurde lang voor ik hier een werkende oplossing voor had.  
- Ophalen van de data van de server en deze inserten in de database, duurt erg lang.. (ik gok door de lange string van de foto's.)  
  
# Decisions  
De belangrijkste beslissing die ik heb genomen, is om naast met de server te werken, ook een database in de app te implementeren. Dit zodat er niet bij elke kleine handeling een get-request gestuurd hoeft te worden. Aangezien ik de vrienden-optie heb weggelaten, was dit prima mogelijk. Aangezien de data relevant voor de gebruiker alleen door de gebruiker zelf aangepast kan worden. Deze database voorkomt onnodige errors en bespaard tijd. Andere kleine beslissingen zijn het weglaten van verwijderknoppen en dit vervangen door OnLongClickListeners en het vervangen van de CategorieActivity door een spinner die de categorieën bevat.  

