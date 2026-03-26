## SOLID
1. S - każda klasa ma tylko jedno zadanie np.
- TypingCalc - liczy wpm acc punkty
- GameViewModel - zarzadza stanem gry
- ScoreRepository - odpowiada za komunikacje z baza danych
2. O - załóżmy, że chciałbym dodać do mojej warstwy data komunikacje z jakimś zewnętrznym API wystarczy dodać odpowiedni interfejs np. RemoteWordRepo ktore jest rozszerzeniem WordRepo(np. Dekorator) i stara logika pozostaje nietknięta tzn. GameViewModel sie w ogole nie zmienia nie interesuje go to czy dane idą z bazy czy z api.
3. L - saveResult(Score: score) można podstawić ArcadeScore/TrainingScore i bedzie dzialac poprawnie() bo  wszystko co obiecuje klasa bazowa będzie dostępne bez zmian i tylko rozszerza funkcjonalność. 
4. I - segregacja interfejsów mam WordRepository, UserRepository, ScoreRepository - są małe i dotyczą tylko jednej rzeczy.
5. D - np. GameViewModel zalezy od ScoreRepository i WordRepository, a nie od implementacji tego interfejsu.
## Wzorce
### Factory Method
- Dlaczego? - bo bez tego ViewModel musiał by decydować jaki chce zbudować wynik, więc mam po prostu jedno miejsce w którym to się będzie działo bez dodawania dodatkowej odpowiedzialności na viewModelu.
- buildScore() metoda w klasie Score

### Command Pattern
>Use the Command pattern when you want to parameterize objects with operations.
>The Command pattern can turn a specific method call into a stand-alone object. This change opens up a lot of interesting uses: you can pass commands as method arguments, store them inside other objects, switch linked commands at runtime, etc.



- Dlaczego i gdzie -  Mam pełno eventów jakie chce obsługiwać tzn. OnTextChange, OnPauseGame, OnRestartGame, etc.. więc każdy z nich to czyste dane - które niosą informacje o tym co się stało na ekranie co użytkownik kliknął etc. więc trzeba to jakoś obsłużyc no i dlatego viewModel będzie zaweirał handlery dla każdej z tych komend i rozsyłał na podstawie typu do odpowiedniej metody.

