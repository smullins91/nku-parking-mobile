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
using GoogleMapsApi;

namespace ParkingManagement.WebContent.ParkingManagement
{
    public partial class ParkingManagement : System.Web.UI.Page
    {
        static string serverAddress = @"http://ec2-54-200-98-161.us-west-2.compute.amazonaws.com:8080";

        //YOU MAY NEED TO DOWNLOAD GoogleMapsAPI from NuGet to make this work

        string[] gotNames;

        protected void Page_Load(object sender, EventArgs e)
        {
            /* load the parking lot names from the database, and then populate the 
             * dropdown list with those names.
             */



      //      DropDownList4.Text = "Row";
      //      DropDownList5.Text = "Column";

            /*
            gotNames = Shared.getLotNames();
            for (int i = 0; i < gotNames.Length; i++)
            {
                DropDownList1.Items.Add(gotNames[i].ToString());
            }
             */
        }

        public enum ViewSelected
        {
            NotSet = -1,
            EditLot = 0,
            AddLot = 1
        }


        protected void Button1_Click(object sender, EventArgs e)
        {
            MultiViewManage.ActiveViewIndex = (int)ViewSelected.EditLot;
        }
        protected void Button6_Click(object sender, EventArgs e)
        {
            MultiViewManage.ActiveViewIndex = (int)ViewSelected.AddLot;
        }


        protected void Button5_Click(object sender, EventArgs e)
        {
            //Include code to add the lot to the lotNames list inside this class
        }

        protected void DropDownList1_SelectedIndexChanged(object sender, EventArgs e)
        {

        }

        public static List<Class1> getParkingLots()
        {
            HttpWebRequest req = WebRequest.Create(serverAddress + "/lots") as HttpWebRequest;
            req.ContentType = "application/json";
            req.Method = WebRequestMethods.Http.Get;
            req.Headers.Add("Authorization", "3addbbc3d6a464eba3f57993411144158b0d312c");

            string result;
            //Rootobject resultObject;
            List<Class1> lotList = new List<Class1>();
            // AllLots resultObject;
            /*
            using (HttpWebResponse resp = req.GetResponse() as HttpWebResponse)
            {
                */
                HttpWebResponse resp = (HttpWebResponse) req.GetResponse();
                StreamReader reader = new StreamReader(resp.GetResponseStream());
                result = reader.ReadToEnd();
                result.Trim();
                //   string stringResult = result.ToString();
          //      string stringResult = result.Substring(1, result.Length - 2);   //We are geting square brackets that make things complicated.
                //When the database has more than 1 lot, it should fix itself.

                lotList = JsonConvert.DeserializeObject<List<Class1>>(result);
             //   resultObject = lotList;
                // resultObject = JsonConvert.DeserializeObject<AllLots>(stringResult);
         /*   }    */
            //return resultObject;
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
        }

        public class Point
        {
            public float lat { get; set; }
            public float lng { get; set; }
        }

        protected void Button9_Click(object sender, EventArgs e)
        {

        }

        protected void Button10_Click(object sender, EventArgs e)
        {

        }

        protected void Button7_Click(object sender, EventArgs e)
        {

        }

        protected void Button8_Click(object sender, EventArgs e)
        {

        }





    }





}