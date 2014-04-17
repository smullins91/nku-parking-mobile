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
using System.Web.Script.Serialization;
using System.Web.Services;

namespace ParkingManagement.WebContent.ParkingManagement
{
    public partial class ParkingManagement : System.Web.UI.Page
    {
        static string serverAddress = @"http://ec2-54-200-98-161.us-west-2.compute.amazonaws.com:8080";

        string[] gotNames;
        public List<Class1> lotsObject;
        //JUST USE http://stackoverflow.com/questions/12133067/load-json-data-for-google-maps-api-v3


        protected void Page_Load(object sender, EventArgs e)
        {
            JavaScriptSerializer serializer = new JavaScriptSerializer();

            //using http://sharepointificate.blogspot.com/2011/09/sending-data-from-codebehind-to.html  Works perfectly and I'm going to build the program around it.
            List<Class1> LotsJSON = getParkingLots();
            string lotsJSONSerialized = serializer.Serialize(LotsJSON);
            string script = String.Format("<script type=\"text/javascript\">var LotCollection={0}</script>", lotsJSONSerialized);
            this.ClientScript.RegisterClientScriptBlock(this.GetType(), "clientScript", script, false);


            Page.DataBind();
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
            //SEE http://www.codeproject.com/Articles/92600/How-to-pass-ASP-NET-server-side-array-to-client-si for instructions on how to load my drop-downs with correct values (may need to use a selection-by-selection set of onclicks to populate each subsequent array)
        }
        protected void Button6_Click(object sender, EventArgs e)
        {
            MultiViewManage.ActiveViewIndex = (int)ViewSelected.AddLot;
        }
        /*
        protected void Button6_ClickCS(object sender, EventArgs e)
        {
            MultiViewManage.ActiveViewIndex = (int)ViewSelected.AddLot;
        }
        */

        protected void Button5_Click(object sender, EventArgs e)
        {
            //Include code to add the lot to the lotNames list inside this class
        }

        protected void DropDownList1_SelectedIndexChanged(object sender, EventArgs e)
        {

        }



        public List<Class1> getParkingLots()
        {
            HttpWebRequest req = WebRequest.Create(serverAddress + "/lots") as HttpWebRequest;
            req.ContentType = "application/json";
            req.Method = WebRequestMethods.Http.Get;
            req.Headers.Add("Authorization", "3addbbc3d6a464eba3f57993411144158b0d312c");

            string result;
            List<Class1> lotList = new List<Class1>();

            HttpWebResponse resp = (HttpWebResponse) req.GetResponse();
            StreamReader reader = new StreamReader(resp.GetResponseStream());
            result = reader.ReadToEnd();
            result.Trim();

            lotList = JsonConvert.DeserializeObject<List<Class1>>(result);
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