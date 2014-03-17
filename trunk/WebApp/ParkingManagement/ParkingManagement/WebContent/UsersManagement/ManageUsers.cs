using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Text;
using System.IO;
using System.Net;
using Newtonsoft.Json;

namespace ParkingManagement.WebContent.UsersManagement
{
    public static class ManageUsers
    {
        static string serverAddress = @"http://ec2-54-200-98-161.us-west-2.compute.amazonaws.com:8080";


        public class User
        {
            //public int userId { get; set; }
            public string UserName { get; set; }
            public string Password { get; set; }
            public int RoleId { get; set; }
            //public string Role { get; set; }
            public string firstName { get; set; }
            public string lastName { get; set; }
            public string email { get; set; }

            public User(){}

            /// <summary>
            /// User constructor to create new user
            /// </summary>
            /// <param name="userName"></param>
            /// <param name="password"></param>
            /// <param name="roleId"></param>
            /// <param name="firstName"></param>
            /// <param name="lastName"></param>
            /// <param name="email"></param>
            public User( string userName, string password, int roleId, string firstName, string lastName, string email)
            {
                this.UserName = userName;
                this.Password = password;
                this.RoleId = roleId;
                this.firstName = firstName;
                this.lastName = lastName;
                this.email = email;

            }
        };

        public class SuperUser:User
        {
            public int userId { get; set; }
            public string Role { get; set; }
            public SuperUser() { }
        }

        public class UserRoles
        {
              public int RoleId { get; set; }
              public string Description { get; set; }
        }

        /// <summary>
        /// Return all users from the database
        /// </summary>
        /// <returns></returns>
        public static List<SuperUser> getUsers()
        {
            HttpWebRequest req = WebRequest.Create(serverAddress + "/users") as HttpWebRequest;
            req.ContentType = "application/json";
            req.Method = WebRequestMethods.Http.Get;

            //TODO: api key should be replaced by the session info when the user log in
            req.Headers.Add("Authorization", "3addbbc3d6a464eba3f57993411144158b0d312c");

            string result;
            List<SuperUser> allUsers;

            using (HttpWebResponse resp = req.GetResponse() as HttpWebResponse)
            {
                StreamReader reader = new StreamReader(resp.GetResponseStream());
                result = reader.ReadToEnd();
                result.Trim();

               // string stringResult = result.Substring(1, result.Length - 2);
                allUsers = (List<SuperUser>)JsonConvert.DeserializeObject(result, typeof(List<SuperUser>));

            }
            return allUsers;
        }

        /// <summary>
        /// Return all roles from the database
        /// </summary>
        /// <returns></returns>
        public static List<UserRoles > getUserRoles()
        {
            HttpWebRequest req = WebRequest.Create(serverAddress + "/roles") as HttpWebRequest;
            req.ContentType = "application/json";
            req.Method = WebRequestMethods.Http.Get;

            //TODO: api key should be replaced by the session info when the user log in
            req.Headers.Add("Authorization", "3addbbc3d6a464eba3f57993411144158b0d312c");

            string result;
            List<UserRoles> allRoles;

            using (HttpWebResponse resp = req.GetResponse() as HttpWebResponse)
            {
                StreamReader reader = new StreamReader(resp.GetResponseStream());
                result = reader.ReadToEnd();
                result.Trim();
                allRoles = (List<UserRoles>)JsonConvert.DeserializeObject(result, typeof(List<UserRoles>));

            }
            return allRoles;
        }

        /// <summary>
        /// Add new user to the database
        /// </summary>
        /// <param name="newUser"></param>
        public static void addUser(User newUser)
        {
            HttpWebRequest req = WebRequest.Create(serverAddress + "/users") as HttpWebRequest;
            req.ContentType = "application/json";
            req.Method = WebRequestMethods.Http.Post;

            //TODO: api key should be replaced by the session info when the user log in
            req.Headers.Add("Authorization", "3addbbc3d6a464eba3f57993411144158b0d312c");

            using (var streamWriter = new StreamWriter(req.GetRequestStream()))
            {

                string json = JsonConvert.SerializeObject(newUser);
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

    }
}