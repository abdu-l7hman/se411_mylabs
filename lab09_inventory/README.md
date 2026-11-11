# Lab 09 – Managing an inventory with the strategy pattern

Run: `clean package exec:java`

**Part 1** – `inventory/Inventory<T>`: generic inventory with `addItem`, `removeItem` and `getAllItems`.
`model/Item` is the parent of `Book` (id, name, author) and `ElectronicDevice` (id, name, category);
`App` stores both in an `Inventory<Item>`.

**Part 2** – `search/SearchStrategy<T>` (functional interface, `matches(T item)`) and
`Inventory.findItems(SearchStrategy<? super T> strategy)`, which returns the matching items.
Strategies: `SearchById`, `SearchByName` (any item), `SearchByAuthor` (books), `SearchByCategory`
(electronic devices). `App` searches a book and a device by name, and also uses lambda expressions
as strategies, for example `inventory.findItems(item -> item.getName().contains("Java"))`.
Thanks to `? super T`, the `SearchStrategy<Item>` strategies also work on an `Inventory<Book>`.

**Part 3** – `App.displayInventory(Inventory<?> inventory)` prints any inventory
(`Inventory<Item>`, `Inventory<Book>`, ...).
