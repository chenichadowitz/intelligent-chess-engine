# intelligent-chess-engine
Automatically exported from code.google.com/p/intelligent-chess-engine

(last modified: April 2011)

Chess engine capable of human-human, computer-human, and computer-computer chess games.

#### Releases are ported from code.google.com/p/intelligent-chess-engine and are not guaranteed to work or function whatsoever. 
#### You are welcome to submit issues and/or pull requests, but please be aware that this project has fallen by the wayside and is no longer maintained.

###List of TODO items to keep track of

####General Items

#####Debug levels
 * 0 = Testing
 * 1 = Errors or warnings (things that are occurring but should not - a.k.a. _NeedToBeFixed_)
 * **2 = _DEFAULT_: Dialogue with player (e.g. moving into check, etc)**
 * 3 = Process changes (moving, generating moves, displaying, etc)
 * 4 = GUI-related messages
 * 5 = Repeated messages (updating pieces, etc)

####Other General Items
 * pipe everything through input/output classes
 * pulling out boardState into Board, then making Game and StaticPosition


####GUI-specific Items

 * ~~separate debug/output message view from notation view (perhaps another text area underneath the board)~~
 * ~~Debug message view area will print all messages~~
 * Setup game dialog
 * Computer difficulty level
