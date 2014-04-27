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
        static string serverAddress = @"http://ec2-54-200-98-161.us-west-2.compute.amazonaws.com:8080";

        protected void Page_Load(object sender, EventArgs e)
        {
            // Ensure user has logged in!!!
            string key = ManageUsers.API_KEY;
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
            gvLots.DataSource = allLots;
            gvLots.DataBind();
            Cache["Lot"] = gvLots.DataSource;

        }

        public static List<Class1> getParkingLots()
        {
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
    }
}