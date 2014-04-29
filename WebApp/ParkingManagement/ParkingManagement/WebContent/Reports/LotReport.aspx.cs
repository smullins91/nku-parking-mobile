using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Net;
using System.IO;
using Newtonsoft.Json;
using ParkingManagement.WebContent.UsersManagement;

namespace ParkingManagement.WebContent.Reports
{
    public partial class LotReport : System.Web.UI.Page
    {
        public static string key;
        static string serverAddress = @"http://ec2-54-200-98-161.us-west-2.compute.amazonaws.com:8080";

        protected void Page_Load(object sender, EventArgs e)
        {
            // Ensure user has logged in!!!
            key = ManageUsers.API_KEY;
            if (key == null || key.Length != 40)
                Response.Redirect(ResolveUrl("~/index.aspx"));

            if (!Page.IsPostBack)
            {
                populateLotGV();
            }

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
            List<Class1> allLots = getParkingLots();
            
            List<SuperSpace> reservations = new List<SuperSpace>();
            
            for (int i = 0; i < allLots.Count(); i++)
            {
                
                    
                    reservations = getSpaces(allLots[i].id);
                    allLots[i].available = allLots[i].available - reservations.Count();
                    allLots[i].percentUsed = 100 - (100 * Convert.ToDecimal((Convert.ToDecimal(allLots[i].available) / Convert.ToDecimal(allLots[i].totalSpaces))));
                    allLots[i].percentUsed = Math.Round(allLots[i].percentUsed, 2);
                
            }
            
            gvLots.DataSource = allLots;
            gvLots.DataBind();
            Cache["Lot"] = gvLots.DataSource;

        }

        public static List<Class1> getParkingLots()
        {
            HttpWebRequest req = WebRequest.Create(serverAddress + "/lots") as HttpWebRequest;
            req.ContentType = "application/json";
            req.Method = WebRequestMethods.Http.Get;
         //   req.Headers.Add("Authorization", "3addbbc3d6a464eba3f57993411144158b0d312c");
            req.Headers.Add("Authorization", key);
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
                lotList[i].percentUsed = 100 - (100 * Convert.ToDecimal((Convert.ToDecimal(lotList[i].available) / Convert.ToDecimal(lotList[i].totalSpaces))));
                lotList[i].percentUsed = Math.Round(lotList[i].percentUsed, 2);
            }
            return lotList;

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
            public string typeString { get; set; }
            public int totalSpaces { get; set; }
            public decimal percentUsed { get; set; }
        }

        public class Point
        {
            public float lat { get; set; }
            public float lng { get; set; }
        }

        
        public static List<SuperSpace> getSpaces(int LOT_ID)
        {
            HttpWebRequest req = WebRequest.Create(serverAddress + "/spaces/" + LOT_ID) as HttpWebRequest;
            req.ContentType = "application/json";
            req.Method = WebRequestMethods.Http.Get;
            //   req.Headers.Add("Authorization", "3addbbc3d6a464eba3f57993411144158b0d312c");
            req.Headers.Add("Authorization", key);

            string result;
            List<SuperSpace> spaceList = new List<SuperSpace>();

            HttpWebResponse resp = (HttpWebResponse)req.GetResponse();
            StreamReader reader = new StreamReader(resp.GetResponseStream());
            result = reader.ReadToEnd();
            result.Trim();

            spaceList = JsonConvert.DeserializeObject<List<SuperSpace>>(result);
            return spaceList;
        }

        public class Space
        {
            public int space { get; set; }
            public int time { get; set; }
            public int user { get; set; }
        }


        public class RootSpace
        {
            public SuperSpace[] Property1 { get; set; }
        }

        public class SuperSpace
        {
            public int ReservationId { get; set; }
            public int SpaceId { get; set; }
            public int UserId { get; set; }
            public DateTime TimeIn { get; set; }
            public DateTime TimeOut { get; set; }
            public int LotId { get; set; }
        }
         
    }
}