# Server

Server für Cooking Consultant

## Bevor Sie starten

Muss GO instaliert werden:

- [Golang](https://golang.org/) (Version 1.9 or above)

1. Nach der Installation erstellen Sie einen workspace

```CLI
%USERPROFILE%\go
```

2. Zum testen der Installation erstellen Sie folgende Dateistruktur

```CLI
%USERPROFILE%\go\src\hello
```

3. Dort erstellen Sie folgende Datei

```CLI
%USERPROFILE%\go\src\hello\hello.go
```

4. Mit folgenden Inhalt

```GOLANG
package main

import "fmt"

func main() {
	fmt.Printf("hello, world\n")
}
```

5. Dann führen Sie ein build aus mit

```CLI
C:\> cd %USERPROFILE%\go\src\hello
%USERPROFILE%\go\src\hello> go build
```

6. Starten Sie das Programm mit

```CLI
%USERPROFILE%\go\src\hello> hello
```

7. Erscheint "hello, world" in der Ausgabe war die Installation erfolgreich

## Installation

1. Erstellen Sie folgende Ordnerstruktur

```CLI
%USERPROFILE%\go\src\syp
```

2. Kopiere Sie den Ordner "proto" in den zuvor erstellten Ordner

3. Wechsel in folgenden Ordner

```CLI
%USERPROFILE%\go\src\syp\end\app\model
```

4. Führen Sie dort folgende Befehle aus

```CLI
go get github.com/gorilla/sessions
go build
go install
```

5. Wechsel in folgenden Ordner

```CLI
%USERPROFILE%\go\src\syp\end\app\controller
```

6. Führen Sie dort folgende Befehle aus

```CLI
go build
go install
```

7. Wechsel in folgenden Ordner

```CLI
%USERPROFILE%\go\src\syp\end
```

8. Führen Sie dort folgende Befehle aus

```CLI
go run cookingconsultantserver.go
```

9. Im erscheinenden Fenster klicken Sie auf zulassen

10. Der Server ist zu erreichen unter
    [localhost:8080/](http://localhost:8080/)
