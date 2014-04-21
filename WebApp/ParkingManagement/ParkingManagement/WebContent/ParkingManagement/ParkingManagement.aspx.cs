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
        static string API_KEY = "3addbbc3d6a464eba3f57993411144158b0d312c";

        string[] gotNames;
        public List<Class1> lotsObject; //need to set this, the AddLot function needs to check it
        //JUST USE http://stackoverflow.com/questions/12133067/load-json-data-for-google-maps-api-v3


        protected void Page_Load(object sender, EventArgs e)
        {
            JavaScriptSerializer serializer = new JavaScriptSerializer();

            //using http://sharepointificate.blogspot.com/2011/09/sending-data-from-codebehind-to.html  Works perfectly and I'm going to build the program around it.
            List<Class1> LotsJSON = getParkingLots();
            lotsObject = LotsJSON; //need to set this, the AddLot function needs to check it
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

        public class NewLot  //used because the new lot uploaded to the server cannot have an id; the server gives it that.
        {
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

        public Point[] getPoints()
        {
            List<Point> pointList = new List<Point>(); //must convert to array and insert into newLot
            if (TextBox7.Text != "")
            {
                Point newPoint = new Point();
                newPoint.lat = float.Parse(TextBox7.Text);
                newPoint.lng = float.Parse(TextBox8.Text);
                pointList.Add(newPoint);
            }
            if (TextBox9.Text != "")
            {
                Point newPoint = new Point();
                newPoint.lat = float.Parse(TextBox9.Text);
                newPoint.lng = float.Parse(TextBox10.Text);
                pointList.Add(newPoint);
            }
            if (TextBox11.Text != "")
            {
                Point newPoint = new Point();
                newPoint.lat = float.Parse(TextBox11.Text);
                newPoint.lng = float.Parse(TextBox12.Text);
                pointList.Add(newPoint);
            }
            if (TextBox13.Text != "")
            {
                Point newPoint = new Point();
                newPoint.lat = float.Parse(TextBox13.Text);
                newPoint.lng = float.Parse(TextBox14.Text);
                pointList.Add(newPoint);
            }
            if (TextBox15.Text != "")
            {
                Point newPoint = new Point();
                newPoint.lat = float.Parse(TextBox15.Text);
                newPoint.lng = float.Parse(TextBox16.Text);
                pointList.Add(newPoint);
            }
            if (TextBox17.Text != "")
            {
                Point newPoint = new Point();
                newPoint.lat = float.Parse(TextBox17.Text);
                newPoint.lng = float.Parse(TextBox18.Text);
                pointList.Add(newPoint);
            }
            if (TextBox19.Text != "")
            {
                Point newPoint = new Point();
                newPoint.lat = float.Parse(TextBox19.Text);
                newPoint.lng = float.Parse(TextBox20.Text);
                pointList.Add(newPoint);
            }
            if (TextBox21.Text != "")
            {
                Point newPoint = new Point();
                newPoint.lat = float.Parse(TextBox21.Text);
                newPoint.lng = float.Parse(TextBox22.Text);
                pointList.Add(newPoint);
            }
            Point[] allPoints = pointList.ToArray();
            return allPoints;
        }

        //Create new lot
        protected void Button11_Click(object sender, EventArgs e)
        {
            //BUILD NEW LOT AND SEND TO SERVER
            NewLot newLot = new NewLot();
            newLot.points = getPoints();
            newLot.lotNumber = TextBox4.Text;
            newLot.type = Convert.ToInt32(DropDownList7.SelectedValue);
            newLot.active = Convert.ToInt32(DropDownList8.SelectedValue);
            newLot.rows = Convert.ToInt32(TextBox5.Text);
            newLot.columns = Convert.ToInt32(TextBox6.Text);
            newLot.available = newLot.rows * newLot.columns;

            InsertLot(newLot);
            //Get all the coordinates, put them in a list, and finally convert to array and insert into newLot
            /*
            if (TextBox7.Text != "")
            {
                Point newPoint = new Point();
                newPoint.lat = float.Parse(TextBox7.Text);
                newPoint.lng = float.Parse(TextBox8.Text);
                pointList.Add(newPoint);
            }
            */

        }
        protected void InsertLot(NewLot inLot)
        {
            //SEE KOMLAVI'S CODE FOR CREATING A NEW USER
            HttpWebRequest req = WebRequest.Create(serverAddress + "/lots") as HttpWebRequest;
            req.ContentType = "application/json";
            req.Method = WebRequestMethods.Http.Post;

            req.Headers.Add("Authorization", API_KEY);

            using (var streamWriter = new StreamWriter(req.GetRequestStream()))
            {
                string json = JsonConvert.SerializeObject(inLot);
                streamWriter.Write(json);
                streamWriter.Flush();
                streamWriter.Close();
            }

            /*
            HttpWebResponse httpResponse = (HttpWebResponse)req.GetResponse();
            using (var streamReader = new StreamReader(httpResponse.GetResponseStream()))
            {
                var result = streamReader.ReadToEnd();
            }
            */
        }
    }
}