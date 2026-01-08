# Java JAR CI/CD with Jenkins

Complete CI/CD pipeline using Jenkins and Kubernetes.

## 🚀 Pipeline Flow
```
GitHub Push → Jenkins → Build JAR → Build Docker → Deploy to K8s → Verify
```

## 🛠️ Technologies

- Java 11
- Maven
- Docker
- Kubernetes (K3d)
- Jenkins

## 📦 Manual Build
```bash
mvn clean package
java -jar target/simple-app-1.0.0.jar
```

## 🌐 Access

- Application: http://localhost:8080
- Jenkins: http://localhost:8090
```

---

## 📋 **STEP 2: CONFIGURE JENKINS**

Now go back to Jenkins (click the **8090** button):

### **2.1: Install Required Plugins**

1. Click **"Manage Jenkins"** → **"Manage Plugins"**
2. Click **"Available plugins"** tab
3. Search and check:
   - ✅ **Pipeline**
   - ✅ **Git plugin**
   - ✅ **Docker Pipeline**
4. Click **"Install without restart"**
5. Wait for installation

---

### **2.2: Configure Maven**

1. **Manage Jenkins** → **"Global Tool Configuration"**
2. Scroll to **"Maven"** section
3. Click **"Add Maven"**
   - Name: `Maven-3.8`
   - **Uncheck** "Install automatically"
   - MAVEN_HOME: `/usr/share/maven`
4. Click **"Save"**

---

## 📋 **STEP 3: CREATE JENKINS PIPELINE JOB**

### **3.1: Create New Job**

1. Click **"New Item"** (left sidebar)
2. Name: `Java-CI-CD-Pipeline`
3. Select **"Pipeline"**
4. Click **"OK"**

---

### **3.2: Configure the Pipeline**

**General Section:**
- Description: `Automated CI/CD pipeline for Java application`

**Build Triggers:**
- ☑️ Check **"Poll SCM"**
- Schedule: `H/5 * * * *` (checks every 5 minutes)

**Pipeline Section:**
- Definition: **"Pipeline script from SCM"**
- SCM: **"Git"**
- Repository URL: `https://github.com/YOUR_USERNAME/java-jenkins-cicd`
  - (Replace `YOUR_USERNAME` with your GitHub username)
- Branch: `*/main` (or `*/master` if your default branch is master)
- Script Path: `Jenkinsfile`

Click **"Save"**

---

## 📋 **STEP 4: RUN YOUR FIRST BUILD!**

1. You're now on the job page
2. Click **"Build Now"** (left sidebar)
3. Watch the build appear under **"Build History"**
4. Click on **"#1"** (the build number)
5. Click **"Console Output"** to watch it run!

You'll see:
```
✅ Stage 1: Checkout
✅ Stage 2: Build JAR
✅ Stage 3: Build Docker
✅ Stage 4: Import to K3d
✅ Stage 5: Deploy to K8s
✅ Stage 6: Wait for Rollout
✅ Stage 7: Verify
