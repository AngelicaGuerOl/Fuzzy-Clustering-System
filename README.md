# 📊 Fuzzy Clustering System (Fuzzy C-Means with Multi-Agent Simulation)

This project is a **Fuzzy Clustering System** developed in **Java**, designed to group data points into clusters using the **Fuzzy C-Means (FCM)** algorithm. In the field of **Artificial Intelligence (AI)**, clustering is an **unsupervised learning technique** that aims to identify patterns or natural groupings in data **without prior labeling**.

---

## 📌 Features:
- Graphical interface for entering points and centroids.
- Automatic calculation of:
- Distance matrix
- Membership matrix (fuzzy values)
- New centroids per iteration
- Cost function and total value `J`
- Real-time graphs of points and centroids.
- Inter-agent communication protocol simulated with **JADE**.
- Interactive buttons to control the process step by step.

---

## 🧠 What is inter-agent communication in this system?

This project simulates an intelligent agent architecture, in which each agent has a specific role and communicates with others using structured messages.

The **JADE** (Java Agent Development Framework) library was used, a platform for developing agents in Java that communicate with each other following the **FIPA ACL (Agent Communication Language)** standard.

### 🧩 How was JADE applied here?

- **GUI Agent**: The agent in charge of the user interface.

This agent receives the parameters (`n`, `c`, `m`, points, centroids) and sends a request to start the clustering process.

- **EXP Agent**: The agent in charge of executing the **Fuzzy C-Means** algorithm.

It receives the request from the GUI agent, performs the calculations, and responds with the results.

Communication between them is done through `REQUEST`, `INFORM`, and `RESULT` messages, which are shown in the **Agent Protocol** section of the interface.

👉 This allows simulating how multiple agents cooperate in a distributed manner to solve an unsupervised learning problem (clustering).

---

## 🧰 Technologies Used

- **Programming Language:** Java
- **Agent Library:** [JADE](https://jade.tilab.com/)
- **Algorithm:** Fuzzy C-Means
- **GUI:** Java Swing
- **IDE:** NetBeans

---
## 📄 Documentation
Detailed documentation is located in the docs/ folder.

---

## 🧑‍💻 Autor

Developed by: [Angelica Guerrero Olvera](https://github.com/AngelicaGuerOl)
