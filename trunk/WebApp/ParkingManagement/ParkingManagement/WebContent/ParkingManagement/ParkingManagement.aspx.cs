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
        //YOU MAY NEED TO DOWNLOAD GoogleMapsAPI from NuGet to make this work

        string[] gotNames;

        protected void Page_Load(object sender, EventArgs e)
        {
            /* load the parking lot names from the database, and then populate the 
             * dropdown list with those names.
             */
            gotNames = Shared.getLotNames();
            for (int i = 0; i < gotNames.Length; i++)
            {
                DropDownList1.Items.Add(gotNames[i].ToString());
            }

            //THIS IS A HACK TO MAKE THE MAP PANEL LARGE ENOUGH, so the rest of the page formats correctly too.
           // Panel1.Height = 460;
             
        }

        


        protected void Button1_Click(object sender, EventArgs e)
        {

        }

       

        protected void Button5_Click(object sender, EventArgs e)
        {
            //Include code to add the lot to the lotNames list inside this class
        }

        protected void DropDownList1_SelectedIndexChanged(object sender, EventArgs e)
        {

        }

        


    }
}