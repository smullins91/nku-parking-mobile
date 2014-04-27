using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Text;
using System.IO;
using System.Net;
using Newtonsoft.Json;
using System.Data;
using System.Configuration;
using System.Data.SqlClient;


namespace ParkingManagement.WebContent.Reports
{
    public partial class Reports : System.Web.UI.Page
    {
        static string serverAddress = @"http://ec2-54-200-98-161.us-west-2.compute.amazonaws.com:8080";
        static string API_KEY = "3addbbc3d6a464eba3f57993411144158b0d312c";

        protected void Page_Load(object sender, EventArgs e)
        {
            populateLotGV();
            //try using http://www.aspdotnet-suresh.com/2013/01/show-google-map-with-latitude-and.html as it has a clearer way of storing the coordinates


            //using http://www.aspsnippets.com/Articles/Show-Google-Maps-using-Latitude-and-Longitude-in-ASPNet.aspx
            if (!this.IsPostBack)
            {

                /*
                DataTable dt = this.getData();
                //rptMarkers.DataSource = dt;
                //rptMarkers.DataBind();
                 */
            }


        }

        private DataTable getData()
        {
            DataTable dt = new DataTable();
            return dt;
        }


        public static string getType(int inNum)
        {
            if (inNum == 1)
                return "Faculty";
            if (inNum == 2)
                return "Student";
            else
                return "Guest";

        }


        private void populateLotGV()
        {
            /*
            //Komlavi's code:
            List<ManageUsers.SuperUser> dtUsers = ManageUsers.getUsers();
            gvUsers.DataSource = dtUsers;
            gvUsers.DataBind();
            Cache["User"] = gvUsers.DataSource;
            */

            List<Class1> allLots = getParkingLots();
            gvLots.DataSource = allLots;
            gvLots.DataBind();
            Cache["Lot"] = gvLots.DataSource;

        }


        // public static List<ParkingLot> getParkingLots()
        //USE http://www.aspsnippets.com/Articles/Show-Google-Maps-using-Latitude-and-Longitude-in-ASPNet.aspx TO GET THE COORDINATE DATA INTO THE FORM THAT JAVASCRIPT CAN USE FOR THE MAP
        
        
        
        
        
        
        
        public static List<Class1> getParkingLots()
        {
            /*
            HttpWebRequest req = WebRequest.Create(serverAddress + "/lots") as HttpWebRequest;
            req.ContentType = "application/json";
            req.Method = WebRequestMethods.Http.Get;
            req.Headers.Add("Authorization", API_KEY);

            string result;
            //List<ParkingLot> allLots;
            //ParkingLot resultObject;
            AllParkingLots allLots;
            using (HttpWebResponse resp = req.GetResponse() as HttpWebResponse)
            {
                StreamReader reader = new StreamReader(resp.GetResponseStream());
                result = reader.ReadToEnd();
                result.Trim();
                //   string stringResult = result.ToString();
                string stringResult = result.Substring(1, result.Length - 2);   //We are geting square brackets that make things complicated.
                //When the database has more than 1 lot, it should fix itself.

                //allLots = (List<ParkingLot>)JsonConvert.DeserializeObject(result, typeof(List<ParkingLot>));  //<ParkingLot>(stringResult);
                // resultObject = JsonConvert.DeserializeObject<AllLots>(stringResult);
                allLots = JsonConvert.DeserializeObject<AllParkingLots>(stringResult);
            }
            return allLots;
             * */

            HttpWebRequest req = WebRequest.Create(serverAddress + "/lots") as HttpWebRequest;
            req.ContentType = "application/json";
            req.Method = WebRequestMethods.Http.Get;
            req.Headers.Add("Authorization", "3addbbc3d6a464eba3f57993411144158b0d312c");

            string result;
            List<Class1> lotList = new List<Class1>();

            HttpWebResponse resp = (HttpWebResponse)req.GetResponse();
            StreamReader reader = new StreamReader(resp.GetResponseStream());
            result = reader.ReadToEnd();
            result.Trim();

            lotList = JsonConvert.DeserializeObject<List<Class1>>(result);
            for (int i = 0; i < lotList.Count(); i++) //need to designate a string version of the lot type
            {
                lotList[i].typeString = getType(lotList[i].type);
                lotList[i].totalSpaces = lotList[i].rows * lotList[i].columns;
                lotList[i].percentUsed = 100 - (100 * (Convert.ToDecimal(lotList[i].available) / Convert.ToDecimal(lotList[i].totalSpaces)));
                lotList[i].percentUsed = Math.Round(lotList[i].percentUsed, 2);
            }
                return lotList;

        }


        public static List<SuperUser> getUsers()
        {
            HttpWebRequest req = WebRequest.Create(serverAddress + "/users") as HttpWebRequest;
            req.ContentType = "application/json";
            req.Method = WebRequestMethods.Http.Get;

            //TODO: api key should be replaced by the session info when the user log in
            req.Headers.Add("Authorization", API_KEY);

            string result;
            List<SuperUser> allUsers;

            using (HttpWebResponse resp = req.GetResponse() as HttpWebResponse)
            {
                StreamReader reader = new StreamReader(resp.GetResponseStream());
                result = reader.ReadToEnd();
                result.Trim();
                allUsers = (List<SuperUser>)JsonConvert.DeserializeObject(result, typeof(List<SuperUser>));

            }
            return allUsers;
        }




        /*
        protected void Button1_Click(object sender, EventArgs e)
        {
            if (MultiView1.ActiveViewIndex > -1)
            {
               // String searchTerm = "";
                ViewSelected mViewSelected =
                     (ViewSelected)MultiView1.ActiveViewIndex;
                switch (mViewSelected)
                {
                    case ViewSelected.ParkingLots:
                       // DoSearch(textProductName.Text, mSearchType);
                        break;
                    case SearchType.Category:
                        DoSearch(textCategory.Text, mSearchType);
                        break;
                    case SearchType.NotSet:
                        break;
                }
            }
        }
        */



        public class User
        {
            public string UserName { get; set; }
            public string Password { get; set; }
            public int RoleId { get; set; }
            public string firstName { get; set; }
            public string lastName { get; set; }
            public string email { get; set; }

            public User() { }

            public User(string userName, string password, int roleId, string firstName, string lastName, string email)
            {
                this.UserName = userName;
                this.Password = password;
                this.RoleId = roleId;
                this.firstName = firstName;
                this.lastName = lastName;
                this.email = email;
            }
        };

        public class SuperUser : User
        {
            public int UserId { get; set; }
            public string Role { get; set; }
            public string Admin { get; set; }

            public SuperUser() { }
            public SuperUser(int UserId, string userName, int roleId, string firstName, string lastName, string email)
            {
                this.UserId = UserId;
                this.UserName = userName;
                this.RoleId = roleId;
                this.firstName = firstName;
                this.lastName = lastName;
                this.email = email;
            }
        }


        public class Rootobject
        {
            public Class1[] Property1 { get; set; }
        }

        public class Class1
        {
            public int id { get; set; }
            public string lotNumber { get; set; }
            public int type { get; set; }
            public int active { get; set; }
            public int rows { get; set; }
            public int columns { get; set; }
            public int available { get; set; }
            public Point[] points { get; set; }
            public string typeString { get; set; } //added for my own purposes
            public int totalSpaces { get; set; } //added for my own purposes
            public decimal percentUsed { get; set; }
        }

        #region "business object"
        public class ParkingLot
        {
            public int id { get; set; }
            public string lotNumber { get; set; }
            public int type { get; set; }
            public int active { get; set; }
            public int rows { get; set; }
            public int columns { get; set; }
            public int available { get; set; }
            public Point[] points { get; set; }
        }

        public class Point
        {
            public float lat { get; set; }
            public float lng { get; set; }
        }

        public class AllParkingLots
        {
            public ParkingLot[] lotList { get; set; }
        }
        
        #endregion
        
    }
}