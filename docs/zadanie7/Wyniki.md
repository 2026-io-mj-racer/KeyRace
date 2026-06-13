## Wyniki testów

| ID testu | Powiązane wymaganie | Rzeczywisty rezultat                                                                                                                 | Wynik |
| -------- | ------------------- | ------------------------------------------------------------------------------------------------------------------------------------ | ----- |
| TC-01    | FR-01               | Po wybraniu trybu 15s test uruchomił się poprawnie, a licznik czasu odliczał od 15 sekund.                                           | OK    |
| TC-02    | FR-01               | Po wybraniu trybu 30s test uruchomił się poprawnie, a licznik czasu odliczał od 30 sekund.                                           | OK    |
| TC-03    | FR-01               | Po wybraniu trybu 60s test uruchomił się poprawnie, a licznik czasu odliczał od 60 sekund.                                           | OK    |
| TC-04    | FR-02               | Po zakończeniu gry w trybie Training Time wyświetlił się ekran statystyk z wynikiem WPM oraz dokładnością ACC.                       | OK    |
| TC-05    | FR-02               | Po zakończeniu gry w trybie Training Words wyświetlił się ekran statystyk z wynikiem WPM oraz dokładnością ACC.                      | OK    |
| TC-06    | FR-02               | Po zakończeniu gry w trybie Arcade wyświetlił się ekran statystyk podsumowujący wynik gracza.                                        | OK    |
| TC-07    | FR-03               | Po zakończeniu gry w trybie Training Time pojawił się przycisk „Play again”, który umożliwiał rozpoczęcie kolejnej gry.              | OK    |
| TC-08    | FR-03               | Po zakończeniu gry w trybie Arcade pojawił się przycisk „Play again”, który umożliwiał ponowne uruchomienie rozgrywki.               | OK    |
| TC-09    | FR-03               | Po zakończeniu gry w trybie Training Words pojawił się przycisk „Play again”, który resetował grę i pozwalał rozpocząć kolejny test. | OK    |
| TC-10    | FR-05               | Tryb Arcade był dostępny w aplikacji i możliwe było rozpoczęcie gry w tym trybie.                                                    | OK    |
| TC-11    | FR-05               | W trybie Arcade możliwy był wybór poziomu trudności przed rozpoczęciem gry.                                                          | OK    |
| TC-12    | FR-05               | W trybie Arcade słowa pojawiały się na ekranie i należało je wpisać zanim spadły, zgodnie z założeniami trybu.                       | OK    |
| TC-13    | FR-09               | Litery, które nie zostały jeszcze wpisane przez użytkownika, były wyświetlane w kolorze szarym.                                      | OK    |
| TC-14    | FR-09               | Poprawnie wpisane litery zmieniały kolor na biały, dzięki czemu użytkownik widział postęp wpisywania tekstu.                         | OK    |
| TC-15    | FR-09               | Po wpisaniu błędnej litery miejsce błędu zostało oznaczone kolorem czerwonym wraz z oczekiwaną literą.                               | OK    |
| TC-16    | FR-10               | Aplikacja umożliwiała wyświetlenie statystyk z poprzednich gier rozegranych w trybie Arcade.                                         | OK    |
| TC-17    | FR-10               | Aplikacja umożliwiała wyświetlenie statystyk z poprzednich gier rozegranych w trybie Training.                                       | OK    |
| TC-18    | FR-10               | Po użyciu opcji resetu statystyki zostały usunięte, a historia wyników została wyczyszczona.                                         | OK    |

