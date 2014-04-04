﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.IO;
using System.Net;
using Newtonsoft.Json;

namespace ParkingManagement.WebContent.Reports
{
    public partial class Reports : System.Web.UI.Page
    {
        static string serverAddress = @"http://ec2-54-200-98-161.us-west-2.compute.amazonaws.com:8080";
        static string API_KEY = "3addbbc3d6a464eba3f57993411144158b0d312c";

        protected void Page_Load(object sender, EventArgs e)
        {
            populateUserGV();
            populateLotGV();
        }

        public enum ViewSelected
        {
            NotSet = -1,
            ParkingLots = 0,
            Users = 1
        }

        protected void Button1_Click(object sender, EventArgs e)
        {
            MultiView1.ActiveViewIndex = (int)ViewSelected.ParkingLots;
           // ViewSelected mViewSelected = (ViewSelected)MultiView1.ActiveViewIndex;
        }

        protected void Button2_Click(object sender, EventArgs e)
        {
            MultiView1.ActiveViewIndex = (int)ViewSelected.Users;
        }

        private void populateLotGV()
        {
            //List<ParkingLot> dataLots = getParkingLots(); //new List<ParkingLot>();
            AllParkingLots dataLots = getParkingLots();
            gvLots.DataSource = dataLots;
            gvLots.DataBind();
        }
        private void populateUserGV()
        {
            List<SuperUser> dtUsers = getUsers();
            gvUserStatus.DataSource = dtUsers;
            gvUserStatus.DataBind();
            Cache["User"] = gvUserStatus.DataSource;
        }


        // public static List<ParkingLot> getParkingLots()
         public static AllParkingLots getParkingLots()
        {
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

        #region "business object"
        public class ParkingLot
        {
            public string lotNumber { get; set; }
            public int type { get; set; }
            public int active { get; set; }
            public Point[] points { get; set; }
        }

        
        public class AllParkingLots
        {
            public ParkingLot[] lotList { get; set; }
        }
        
        #endregion

    }
}