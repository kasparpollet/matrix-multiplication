# Parallel Computing
### Matrix Multiplication


Instructies om het project te installeren. <br><br>
<strong>Vereisten:</strong>
- Java versie 17 geinstalleerd.
- Een IDE geinstalleerd zoals IntelliJ of Eclipse.
- Allen voor 3-ParallelDistributed moet ActiveM1 geinstaleerd zijn.

<strong>Instructries om het project te installeren:</strong>
1. Maak een aan map waarin je het project wilt hebben.
2. Clone het project, voer deze commando uit in een terminal in de path van de bovenstaande map:
   1. Met SSH: <code>$ git clone git@gitlab.fdmci.hva.nl:parcom/21-22-2/ivse3/team05.git</code>.
   2. Met HTTPS: <code>$ git clone https://gitlab.fdmci.hva.nl/parcom/21-22-2/ivse3/team05.git </code>.

<strong>Instructries om het project te runnen:</strong> <br>
Er zijn 3 subprojecten aanwezig, namelijke <code>1-Sequential</code>, <code>2-ParallelThreadLocks</code> en <code>3-ParallelDistributed</code>. Deze kunnen individueel gerunned worden.

<strong>1-Sequential</strong>
1. Run de Main.java file.
2. Het programma zal het vragen voor de grootte van de twee matrices die met elkaar vermenigvuldigd zullen zijn, vul hier een integer nummer in. Dit wordt gebruikt voor de lengte en breedte van beide matrices.
3. Het programma runt en verteld hoelang het heeft geduurt.

<strong>2-ParallelThreadLocks</strong>
1. Run de Main.java file.
1. Het programma zal het vragen voor de grootte van de twee matrices die met elkaar vermenigvuldigd zullen zijn, vul hier een integer nummer in. Dit wordt gebruikt voor de lengte en breedte van beide matrices.
2. Het programma zal je vervolgends vragen voor het aantal threads die aangemaakt zullen worden om de berekeningen uit te voeren, voer hier ook een integer nummer in. (aanbevolen, kijk eerst hoeveel je computer aan kan)
3. Het programma runt en verteld hoelang het heeft geduurt voor elk van de drie implementiaties van het algoritme.

<strong>3-ParallelDistributed</strong>
1. Run Producer.java file.
2. Het programma zal het vragen voor de grootte van de twee matrices die met elkaar vermenigvuldigd zullen zijn, vul hier een integer nummer in. Dit wordt gebruikt voor de lengte en breedte van beide matrices.
3. Het programma zal je vervolgends vragen voor het aantal stop berichten die gemaakt moet worden aan het einde van de berekening om de een aantal consumer te stoppen.
4. Run nu de Consumer.java file zo vaak als dat je wil. Dit zijn de programma's die tegelijkertijd de berkeningen uit gaan voeren.
5. Als het klaar is vertled de Producer hoelang het heeft geduurt.
6. Om mee te kijken in de Queues, ga naar http://localhost:8161/
