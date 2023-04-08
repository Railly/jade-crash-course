# Jade Crash Course

- Maven project based on [JADE Tutorial in Portuguese by Andre Filipe – Federal University of ABC – Sao Paulo, Brazil](https://jade.tilab.com/doc/tutorials/noEnglish/ManualJadePortuguese.pdf)

- Some parts of the code is adapted in my own way from the tutorial.
- I added a pretty logger in order to differentiate the messages from the agents.

# How to run

- I did not use any IDE, just the command line.
- Basically you need to run `wvn clean install` and `mvn compile`
- Then you execute any agent you want, for example `java -cp target/classes jade.Boot -gui -agents "agent1:agents.Agent1;agent2:agents.Agent2;agent3:agents.Agent3"`

> **Note**
>
> - The agents are in the package `agents`
> - I used Windows, so I had to use `;` instead of `:` in the command line

# License

- MIT License
