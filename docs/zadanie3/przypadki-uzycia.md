## 1. Wybór trybu gry

### Warunki wstępne
Użytkownik znajduje się na ekranie głównym aplikacji.System wyświetla ostatnio używane ustawienia gry.

---
### Scenariusz główny

1. Użytkownik wybiera jeden z dwóch dostępnych trybów gry: **Arcade** lub **Training**.
2. System reaguje w zależności od wybranego trybu:
   - **2a. Tryb Training:**
     - Użytkownik wybiera wariant sesji: **na czas** lub **na słowa**.
     - Użytkownik określa wartość: **ilość czasu** (np. 30s, 60s) lub **ilość słów** (np. 25, 50).
   - **2b. Tryb Arcade:**
     - Tryb nie wymaga dodatkowej konfiguracji.
     - System automatycznie ustawia parametry sesji.
3. System przechodzi do stanu gotowości – widoczny jest przycisk **Start/Play**.

---
### Scenariusz alternatywny

**1a.** Użytkownik nie wybiera żadnego trybu gry:
  - System przyjmuje domyślne parametry sesji - training 15s.
  - System przechodzi do stanu gotowości – widoczny jest przycisk **Start/Play**.
**1b.** Użytkownik zmienia zdanie(wcześniej wybrany tryb gry)
  - System aktualizuje ekran do konfiguracji nowego trybu
  - System przywraca ostatnio używane parametry dla tego trybu.
  - Scenariusz wraca do kroku 2.

---
### Warunki końcowe
Aplikacja jest w stanie gotowym do rozpoczęcia sesji pisania – na ekranie widoczny jest przycisk **Start/Play** z ustawionymi parametrami gry (domyślnymi lub wybranymi przez użytkownika).

## 2. Rozpoczęcie sesji pisania

### Warunki wstępne
Użytkownik wybrał parametry i system jest gotowy do rozpoczęcia sesji gry,

---
### Scenariusz Główny

1. Użytkownik klika 'Play'/'Start' na ekranie
2. System przenosi go do nowego ekranu i wyświetla klawiaturę oraz tekst/pierwsze słowo które użytkownik musi wpisać.
3. W zależności od trybu
	**3a.** W trybie arcade system wyświetla odliczanie 3...2..1 'start'
	**3b.** W trybie training system czeka na kliknięcie pierwszej litery tekstu żeby zacząć grę.
4. System wyświetla informacje dotyczące rozpoczętej sesji ilość słów/czasu do końca lub ilość punktów.

--- 
### Scenariusz Alternatywny
**1a.** Użytkownik cofa się do ekranu głównego klikając na jakiś back button:
	- System wraca do ekranu głównego 
	- Ustawienia zostają zapamiętane 
**3c.** Użytkownik nie klika żadnego klawisza lub nie odpowiedni klawisz i system pozostaje w stanie oczekiwania, gra się nie rozpoczyna dopóki nie zostanie wprowadzony pierwszy znak tekstu.

--- 
### Warunki Końcowe
(rozpoczęcie gry)
Sesja jest aktywna - tekst widoczny na ekranie,
System rejestruje input od użytkownika
Wyświetlane są informacje dotyczące sesji

(przerwanie)
Użytkownik wrócił do ekranu głównego.

## 3. Rozegranie sesji

### Warunki początkowe
Użytkownik rozpoczął sesje tzn. wpisał pierwszą literę testu i system wyświetla informacje dotyczące trwającej sesji

--- 
### Scenariusz Główny

1. Użytkownik wpisuje kolejno litery, które są lub nie w teście.
2. System aktualizuje UI przesuwając kursor i :
	 2a. Jeżeli litera była błędna sygnalizuje to na czerwono, ale użytkownik może pisać dalej
	 2b. Jeżeli litera była poprawna, kolor litery się zmienia nieznacznie
3. Gdy test się kończy system chowa klawiaturę i wyświetla ekran Game over ze statystykami dotyczącymi sesji.

---
### Scenariusz Alternatywny

- **1a.** Użytkownik klika play again w trakcie sesji: 
	- System przenosi użytkownika do ekranu początkowego sesji i oczekuje na pierwszy znak
- **1b.** Użytkownik cofa się - backspace
	- System usuwa ostatnio wprowadzony znak
	- Kursor wraca do poprzedniej pozycji

---
### Warunki końcowe
(Prawidłowe rozegranie)
System wyświetla informacje o sesji - wpm, acc w trybie treningowym, w trybie arcade ilość zdobytych punktów.

(kliknięcie play again podczas testu)
System powraca do stanu oczekiwania na pierwszą literę testu bez zapisania wyników.

## 4. Wyświetlenie wyników gry

### Warunki początkowe
Użytkownik zakończył sesję gry i poczekał na render wszystkich statystyk.

---
### Scenariusz główny

1. System wyświetla statystyki dotyczące gry
	**1a.** W trybie treningowym : WPM, ACC, ilość napisanych słów lub czas
	**1b.** W trybie arcade: liczba zdobytych punktów, np liczba zestrzelonych słów
2. System zapisuje wynik lokalnie
3. System wyświetla dostępne opcję : Play again, Menu
4. Użytkownik wybiera jedną z opcji:
	**4a.** Użytkownik klika "Play again"
	- System przenosi go do ekranu rozpoczęcia sesji
	**4b.** Użytkownik klina "Home/Menu"
	- System przenosi uzytkownika do ekranu głównego

---
### Warunki końcowe
Wszystkie statystyki sesji są widoczne na ekranie, wynik został zapisany lokalnie, użytkownik może wybrać dalszą akcję (Play Again lub Menu).

## 5. Przeglądanie statystyk
### Warunki początkowe
Użytkownik jest na ekranie głównym aplikacji

---
### Scenariusz główny

1. Użytkownik klika ikonkę profilu
2. System przenosi na widok profilu ze statystykami dotyczącymi dwóch trybów.
3. Użytkownik może analizować statystyki dla
	**3a. Trybu trenignowego
	- najlepszy wpm, acc
	- średni wpm, acc
	- liczba rozegranych gier
	- łączny czas spędzony na pisaniu
	3b. Tryb arcade
	- najlepszy wynik w pkt.
	- średni wynik pkt.
	- liczba rozegranych gier
	\*dodatkowo widoczne jest <10 najelpszych wyników
	

---
### Scenariusz Alternatywny

- 1a. Brak zapisanych danych bo użytkownik nie rozegrał jeszcze ani jednej gry:
	- system wyświetla komunikat "brak danych"
	- statystyki wyświetlają wartości zerowe

---
### Warunki końcowe
Ekran statystyk jest widoczny, dane dla obu trybów są wyświetlone poprawnie, lista 10 najlepszych wyników jest widoczna.(jeżeli dane są)


## 6. Resetowanie Statystyk
### Warunki początkowe
Użytkownik ma dostępne jakieś statystyki(rozegrał już gry) i jest na ekranie profilu.

---
### Scenariusz główny

1. Użytkownik wchodzi w ustawienia profilu
2. System przekierwouje go do widoku ustawień
3. Użytkownik wybiera opcję "reset data" 
4. System prosi go o potwierdzenie operacji
5. Użytkownik potwierdza operację.
6. System usuwa dane statystyczne z lokalnej bazy danych
7. System przenosi użytkownika do ekranu profilu z wyzerowanymi statystykami.
---
### Scenariusz alternatywny

5a. Użytkownik anuluje operacje:
- System zamyka dialog i dane pozostają niezmienion

---
### Warunki końcowe
(potwierdzenie resetu)
Wszystkie dane  zostały trwale usunięte z lokalnej bazy danych. Ekran profilu wyświetla wartości zerowe lub "--".

(Anulowanie operacji) Dane pozostają niezmienione, użytkownik wraca do widoku ustawień.


## BONUS: 7. Rozegranie dziennego testu
### Warunki początkowe
Użytkownik jest na ekranie głównym apki, urządzenie ma połączenie z internetem.

---
### Scenariusz główny

1. Użytkownik klika 'Daily challenge'
2. System wysyła zapytanie do zewnętrznego API
3. API zwraca ciekawostkę/cytat jako tekst dzienny
4. System wyświetla test z pobranym tekstem.
5. Użytkownik rozpoczyna grę z tym tekstem.

---
### Scenariusz alternatywny
**3a.** API zwraca błąd:
- System wyświetla komunikat o błędzie

---
### Warunki końcowe
Tekst dzienny jest widoczny i system jest gotowy do rozpoczęcia gry.

(Api zwraca błąd)
Użytkownik jest informowany o problemie.