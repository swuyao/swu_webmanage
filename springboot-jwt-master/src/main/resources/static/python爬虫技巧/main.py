from flask import *
import json
from flaskext.mysql import MySQL

mysql = MySQL()
app = Flask(__name__)
app.config['MYSQL_DATABASE_USER'] = 'root'
app.config['MYSQL_DATABASE_PASSWORD'] = '123456'
app.config['MYSQL_DATABASE_DB'] = 'CalMap'
app.config['MYSQL_DATABASE_HOST'] = 'mysql'
mysql.init_app(app)


app.secret_key = 'random string'
UPLOAD_FOLDER = 'static/uploads'
ALLOWED_EXTENSIONS = set(['jpeg', 'jpg', 'png', 'gif'])
app.config['UPLOAD_FOLDER'] = UPLOAD_FOLDER
cursor = mysql.connect().cursor()
@app.route("/")
def root():
    return render_template('home.html')

@app.route("/login")
def login():
    return render_template('login.html')

@app.route("/hello", methods=['POST'])
def hello():
    data = json.loads(request.get_data().decode())
    print(data)
    map = {"data":"123"}
    return json.dumps(map)

@app.route("/register", methods=['POST'])
def register():
    data = json.loads(request.get_data().decode())
    print(data)
    userName = data["userName"]
    map = {"data":"123"}
    return json.dumps(map)

@app.route("/Authenticate")
def Authenticate():
    username = request.args.get('UserName')
    password = request.args.get('Password')
    cursor = mysql.connect().cursor()
    cursor.execute("select * from User")

    data = cursor.fetchone()
    print(data)
    if data is None:
        return "Username or Password is wrong"
    else:
        return "Logged in successfully"

if __name__ == '__main__':
    app.run(debug=True)
    
