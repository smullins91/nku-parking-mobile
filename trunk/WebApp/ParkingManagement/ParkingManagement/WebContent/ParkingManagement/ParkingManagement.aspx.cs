using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Text;
using System.IO;
using System.Net;
using System.Data;
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
        string key;
        static int selectedLotId; //Used in the lot edit function. Held here because it can't be part of the lot object, but I still need it.
        public List<SuperSpace> lotSpaces;

        string[] gotNames;
        public List<Class1> lotsObject; //need to set this, the AddLot function needs to check it
        //JUST USE http://stackoverflow.com/questions/12133067/load-json-data-for-google-maps-api-v3


        protected void Page_Load(object sender, EventArgs e)
        {
            key = UsersManagement.ManageUsers.API_KEY;
            if (key == null || key.Length != 40)
                Response.Redirect(ResolveUrl("~/index.aspx"));


            //FOR MAP VIEW
            JavaScriptSerializer serializer = new JavaScriptSerializer();
            //using http://sharepointificate.blogspot.com/2011/09/sending-data-from-codebehind-to.html  Works perfectly and I'm going to build the program around it.
            List<Class1> LotsJSON = getParkingLots();
            Rootobject rootLots = new Rootobject();
            rootLots.Property1 = LotsJSON.ToArray();
            lotsObject = LotsJSON; //need to set this, the AddLot function needs to check it
            string lotsJSONSerialized = serializer.Serialize(LotsJSON);
            string script = String.Format("<script type=\"text/javascript\">var LotCollection={0}</script>", lotsJSONSerialized);
            this.ClientScript.RegisterClientScriptBlock(this.GetType(), "clientScript", script, false);
            

            //FOR EDIT VIEW
            if (DropDownList1.Items.Count <= 1) //this is needed to prevent multiple entries into the dropdown.
            {
                
                DataTable lotTable = new DataTable(); //make a data table, as we must populate the dropdownlist wth both text and values
                    lotTable.Columns.Add("name", typeof(string));
                    lotTable.Columns.Add("value", typeof(int));
                for (int i = 0; i < lotsObject.Count; i++)
                {
                    lotTable.Rows.Add(lotsObject[i].lotNumber, i);
                }
                DropDownList1.DataSource = lotTable;
                DropDownList1.DataTextField = "name";
                DropDownList1.DataValueField = "value";
            }


            Page.DataBind();
        }




        public String[] getLotNames(Rootobject inRoot)
        {
            List<String> names = new List<String>();
            for (int i = 0; i < inRoot.Property1.Count(); i++) //Class1 Property1 in inRoot)
            {
                names.Add(inRoot.Property1[i].lotNumber);
            }
            return names.ToArray();
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
            DropDownList1_SelectedIndexChanged(null, null); //make it populate with the values for the lot that's selected by default.
            //SEE http://www.codeproject.com/Articles/92600/How-to-pass-ASP-NET-server-side-array-to-client-si for instructions on how to load my drop-downs with correct values (may need to use a selection-by-selection set of onclicks to populate each subsequent array)
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
            Class1 selectedLot = lotsObject[Convert.ToInt32(DropDownList1.SelectedItem.Value)];
            DropDownList2.SelectedValue = selectedLot.type.ToString();
            DropDownList3.SelectedValue = selectedLot.active.ToString();
            TextBox1.Text = selectedLot.columns.ToString();
            TextBox2.Text = selectedLot.rows.ToString();

            selectedLotId = selectedLot.id;

            //Populate the boxes for editing a specific space
            //REMEMBER: These boxes start the numbering at 1, so I'll need to adjust accordingly when changing the space.
            DropDownList4.Items.Clear();
            for (int i = 1; i <= selectedLot.columns; i++)
            {
                DropDownList4.Items.Add(new ListItem(i.ToString(), i.ToString()));
            }
            DropDownList5.Items.Clear();
            for (int i = 1; i <= selectedLot.rows; i++)
            {
                DropDownList5.Items.Add(new ListItem(i.ToString(), i.ToString()));
            }


            //Get the spaces in the lot
            lotSpaces = getSpaces(selectedLot.id);

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
        { //Apparently these things may all need to be capitalized.
            public string LotNumber { get; set; }
            public int TypeId { get; set; }
            public int Active { get; set; }
            public int Rows { get; set; }
            public int Columns { get; set; }
            public float[][] Points { get; set; }
        }

        public class UpdatedLot  //used because the JSON to the server cannot have coordinates when updating the lot
        {
            public string LotNumber { get; set; }
            public int TypeId { get; set; }
            public int Active { get; set; }
            public int Rows { get; set; }
            public int Columns { get; set; }
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

        protected void Button7_Click(object sender, EventArgs e) //Save/update existing lot
        {
            UpdatedLot tempLot = new UpdatedLot();
            tempLot.LotNumber = DropDownList1.SelectedItem.ToString();
            tempLot.TypeId = Convert.ToInt32(DropDownList2.SelectedValue);
            tempLot.Active = Convert.ToInt32(DropDownList3.SelectedValue);
            tempLot.Rows = Convert.ToInt32(TextBox2.Text);
            tempLot.Columns = Convert.ToInt32(TextBox1.Text);

            UpdateLot(tempLot);
        }

        protected void Button8_Click(object sender, EventArgs e)
        {

        }

        public float[][] getPoints2()
        {
            List<float[]> pointList = new List<float[]>();
            
            if (TextBox7.Text != ""){
                pointList.Add(new float[]{Convert.ToSingle(TextBox7.Text), Convert.ToSingle(TextBox8.Text)});
            }
            if (TextBox9.Text != ""){
                pointList.Add(new float[]{Convert.ToSingle(TextBox9.Text), Convert.ToSingle(TextBox10.Text)});
            }
            if (TextBox11.Text != ""){
                pointList.Add(new float[]{Convert.ToSingle(TextBox11.Text), Convert.ToSingle(TextBox12.Text)});
            }
            if (TextBox13.Text != ""){
                pointList.Add(new float[]{Convert.ToSingle(TextBox13.Text), Convert.ToSingle(TextBox14.Text)});
            }
            if (TextBox15.Text != ""){
                pointList.Add(new float[]{Convert.ToSingle(TextBox15.Text), Convert.ToSingle(TextBox16.Text)});
            }
            if (TextBox17.Text != ""){
                pointList.Add(new float[]{Convert.ToSingle(TextBox17.Text), Convert.ToSingle(TextBox18.Text)});
            }
            if (TextBox19.Text != ""){
                pointList.Add(new float[]{Convert.ToSingle(TextBox19.Text), Convert.ToSingle(TextBox20.Text)});
            }
            if (TextBox21.Text != ""){
                pointList.Add(new float[]{Convert.ToSingle(TextBox21.Text), Convert.ToSingle(TextBox22.Text)});
            }

            return pointList.ToArray();
        }

        //Create new lot
        protected void Button11_Click(object sender, EventArgs e)
        {
            NewLot newLot = new NewLot();
            newLot.Points = getPoints2();
            newLot.LotNumber = TextBox4.Text;
            newLot.TypeId = Convert.ToInt32(DropDownList7.SelectedValue);
            newLot.Active = Convert.ToInt32(DropDownList8.SelectedValue);
            newLot.Rows = Convert.ToInt32(TextBox5.Text);
            newLot.Columns = Convert.ToInt32(TextBox6.Text);

            InsertLot(newLot);

        }
        protected void UpdateLot(UpdatedLot inLot)
        {
            HttpWebRequest req = WebRequest.Create(serverAddress + "/lots" + "/" + selectedLotId) as HttpWebRequest;
            req.ContentType = "application/json";
            req.Method = WebRequestMethods.Http.Post;

            req.Headers.Add("Authorization", key);

            using (var streamWriter = new StreamWriter(req.GetRequestStream()))
            {
                string json = JsonConvert.SerializeObject(inLot);
                streamWriter.Write(json);
                streamWriter.Flush();
                streamWriter.Close();
            }


            HttpWebResponse httpResponse = (HttpWebResponse)req.GetResponse();
            using (var streamReader = new StreamReader(httpResponse.GetResponseStream()))
            {
                var result = streamReader.ReadToEnd();
            }

        }

        protected void InsertLot(NewLot inLot)
        {
            HttpWebRequest req = WebRequest.Create(serverAddress + "/lots") as HttpWebRequest;
            req.ContentType = "application/json";
            req.Method = WebRequestMethods.Http.Post;

            req.Headers.Add("Authorization", key);

            using (var streamWriter = new StreamWriter(req.GetRequestStream()))
            {
                string json = JsonConvert.SerializeObject(inLot);
                streamWriter.Write(json);
                streamWriter.Flush();
                streamWriter.Close();
            }

            
            HttpWebResponse httpResponse = (HttpWebResponse)req.GetResponse();
            using (var streamReader = new StreamReader(httpResponse.GetResponseStream()))
            {
                var result = streamReader.ReadToEnd();
            }
        }

        protected void DropDownList4_SelectedIndexChanged(object sender, EventArgs e)
        {
            showSpaceInfo(); //As admin needs the same update when either column or row is changed, have both use the same function
        }

        protected void DropDownList5_SelectedIndexChanged(object sender, EventArgs e)
        {
            showSpaceInfo(); //As admin needs the same update when either column or row is changed, have both use the same function
        }

        public void showSpaceInfo()
        {
            Class1 selectedLot = lotsObject[Convert.ToInt32(DropDownList1.SelectedItem.Value)];
            // Will fill DropDownList6 and TextBox3
            int columnSelected = Convert.ToInt32(DropDownList4.SelectedItem.Value);
            int rowSelected = Convert.ToInt32(DropDownList5.SelectedItem.Value);

            int spaceSelected = columnSelected * selectedLot.rows + rowSelected;

            TextBox3.Text = Convert.ToString(lotSpaces[spaceSelected].UserId);
            
            TextBox3.Text = "Test passed"; //TESTNG
            /*REMEMBER: Spaces are numbered in the server as numbers 0 through Rows*Columns-1. 
             * I'll need to do the math to determine the row and column 
             * (simple addition of 1 (can't have space 0) and then the modulus equation).
             * DOWNLOAD the space info when user selects a lot.
            */

            //May have error sending reservation to server, if the JSON for that needs the type names to be capitalized
        }

        public List<SuperSpace> getSpaces(int LOT_ID)
        {
            HttpWebRequest req = WebRequest.Create(serverAddress + "/spaces/" + LOT_ID) as HttpWebRequest;
            req.ContentType = "application/json";
            req.Method = WebRequestMethods.Http.Get;
            req.Headers.Add("Authorization", "3addbbc3d6a464eba3f57993411144158b0d312c");

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