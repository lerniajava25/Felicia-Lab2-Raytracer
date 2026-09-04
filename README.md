
# Raytracer — Laboration 2 (OOP)

En enkel **raytracer** skriven i Java. Programmet skickar strålar (`Ray`) från en fast kamera in i en scen och beräknar vilka objekt (`Shape`) som träffas. Resultatet renderas sedan till en `.bmp`-bild.

Implementationen inkluderar även **skuggor** via en punktljuskälla (`PointLight`).

Klassen `Main` sätter upp en `Scene` med en ljuskälla och några `Shape`-objekt, renderar en bild och sparar den som "renderedImage.bmp" i projektets rotmapp.

---

## Projektstruktur

```text
org.raytracer
│
├── Main
│
├── math
│   ├── Vector3D
│   ├── Ray
│   └── Color
│
├── light
│   └── PointLight
│
├── geometry
│   ├── Shape
│   ├── Intersection
│   ├── Sphere
│   └── Triangle
│
├── render
│   └── Scene
│
└── io
    └── BmpWriter
```

---

# Klasser

## `org.raytracer`

* `Main:` Bygger upp en `Scene` med ljus och former, renderar bilden och skriver ut den till en `.bmp`-fil. 

---

## `org.raytracer.math`

*  `Vector3D:` Record för en 3D-vektor. Innehåller metoder för addition, subtraktion, skalning, dot-produkt, cross-produkt, längd och normalisering. Används för positioner, riktningar och ytnormaler. 
* `Ray:` Record som representerar en stråle med en startpunkt (`origin`) och en riktning (`direction`). Metoden `pointAt(t)` ger punkten längs strålen enligt `t`: `P = O + tD`. 
* `Color:`  Record för en RGB-färg. Har metoder för addition, skalning, multiplikation och `clamp()`, som begränsar RGB-värdena mellan `0–255`. 

---

## `org.raytracer.light`

*`PointLight:` Representerar en punktljuskälla med en position och en färg. Används av `Scene` för att beräkna skuggor och belysning vid träffpunkter.

---

## `org.raytracer.geometry`

* `Shape:`  Abstrakt basklass för alla geometriska objekt i scenen. Definierar den gemensamma metoden `hit(Ray ray)`, som returnerar en `Optional<Intersection>`. Alla former ärver från denna klass.
*`Intersection:` Record som beskriver en träff mellan en stråle och en form: avståndet `t` längs strålen, träffpunkten `point`, ytnormalen `normal` och formens färg `color`. 
* `Sphere:` Implementerar `Shape`. Ett klot definierat av en centrumpunkt och en radie. Metoden `hit()` löser skärningen med hjälp av en andragradsekvation. 
*`Triangle:` Implementerar `Shape`. En triangel definierad av tre hörnpunkter (`v0`, `v1`, `v2`). Metoden `hit()` använder Möller–Trumbore-algoritmen för att beräkna skärning med strålen. 

---

## `org.raytracer.render`

* `Scene:` Håller scenens tillstånd: en `List<Shape>`, en bakgrundsfärg och en `PointLight`. Ansvarar för renderingen genom att konvertera canvas-koordinater till strålar, hitta närmaste träff (`traceRay`), beräkna skuggor (`isInShadow`) och returnera en `Color[][]` som representerar den färdiga bilden. 

---

## `org.raytracer.io`

* `BmpWriter:` Skriver en `Color[][]`-bild till en `.bmp`-fil med hjälp av `BufferedImage` och `ImageIO`. 
---

# Lägga till en ny `Shape`

Programmet är byggt enligt **Open/Closed-principen**. Nya geometriska former kan därför läggas till utan att ändra i `Scene`, `Ray` eller den befintliga renderingslogiken.

## 1. Skapa en ny klass

Skapa en ny klass i paketet, exempelvis `Box` 

```text
org.raytracer.geometry
```

## 2. Klassen ärver från `Shape`

Låt klassen ärva från `Shape` och skicka med färgen till basklassen:

```java
public class Box extends Shape {

    public Box(Color color) {
        super(color);
    }

    @Override
    public Optional<Intersection> hit(Ray ray) {
        // Beräkna skärning mellan ray och formen.

        // Om strålen träffar formen:
        // return Optional.of(
        //     new Intersection(t, point, normal, color)
        // );

        // Annars:
        return Optional.empty();
    }
}
```

## 3. Implementera skärningslogiken

Lägg till den geometriska logiken för den nya formen.

Se de befintliga implementationerna som exempel:

- `Sphere` använder en **andragradsekvation**.
- `Triangle` använder **Möller–Trumbore-algoritmen**.

## 4. Lägg till formen i scenen

Initiera formen i `Main` och lägg till den i scenen.

## VG - Skuggor och ljuskällor
Skuggor beräknas i Scene. När en stråle från kameran träffar ett objekt vet vi träffpunkten och ytans normal. Därifrån skickas en ny “skuggstråle” mot ljuskällans position. Startpunkten för den strålen flyttas en liten bit ut så att den inte råkar krocka med sin egen yta direkt.

Ljuskällan representeras av klassen “PointLight”, som bara håller en position och en färg. Den läggs in i Scene och används både för att peka ut vart skuggstrålarna ska riktas och för att avgöra vilken färg som ska blandas in i belysta ytor.

Om skuggstrålen träffar ett annat objekt innan den når fram till ljuskällan betyder det att något är i vägen, och punkten ligger då i skugga. Färgen dämpas i det fallet istället för att, så att formerna fortfarande syns lite i skuggan. Om ingenting blockerar vägen beräknas färgen som vanligt, alltså genom att objektets färg kombineras med ljusets färg.







